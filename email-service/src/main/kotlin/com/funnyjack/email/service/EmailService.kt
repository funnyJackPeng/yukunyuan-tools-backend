package com.funnyjack.email.service

import com.funnyjack.email.template.generateJoinApplicationTemplate
import com.funnyjack.persistent.entity.JoinApplicationRepository
import com.funnyjack.persistent.entity.SystemConfig
import com.funnyjack.persistent.entity.UserInfo
import com.funnyjack.persistent.entity.UserInfoRepository
import com.funnyjack.persistent.service.SystemConfigService
import org.apache.coyote.BadRequestException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val userInfoRepository: UserInfoRepository,
    private val joinApplicationRepository: JoinApplicationRepository,
    private val systemConfigService: SystemConfigService
) {
    fun sendJoinApplication(openId: String) {
        val userInfo = userInfoRepository.findByOpenid(openId) ?: throw BadRequestException("请先登录！")
        val joinApplication = joinApplicationRepository.findByUserInfoOpenid(openId)
            ?: throw BadRequestException("请先配置加入申请邮件的模版")
        val joinApplicationAddressee = systemConfigService[SystemConfig.Key.EmailAddressee.joinApplicationAddressee]
        val (emailSubject, content) = generateJoinApplicationTemplate(joinApplication)
        send(joinApplicationAddressee, userInfo, emailSubject, content)
    }

    private fun send(
        addressee: String,
        userInfo: UserInfo,
        subject: String,
        content: String
    ) {
        if (addressee.isBlank()) {
            throw BadRequestException("收件人不存在，请联系管理员配置")
        }
        val emailCompany = userInfo.emailCompany ?: throw BadRequestException("请先配置自己的邮箱")
        val userEmail = "${userInfo.localPart}${emailCompany.domain}"
        SimpleMailMessage().apply {
            this.setTo(addressee)
            this.from = userEmail
            this.subject = subject
            this.text = content
        }.let {
            JavaMailSenderImpl().apply {
                host = emailCompany.host
                port = emailCompany.port
                username = userEmail
                password = userInfo.emailAuthCode
            }.send(it)
        }
    }
}
