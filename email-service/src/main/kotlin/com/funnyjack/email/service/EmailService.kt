package com.funnyjack.email.service

import com.funnyjack.email.template.generateDonationApplicationTemplate
import com.funnyjack.email.template.generateJoinApplicationTemplate
import com.funnyjack.persistent.entity.*
import com.funnyjack.persistent.service.SystemConfigService
import com.hiczp.spring.error.BadRequestException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val userInfoRepository: UserInfoRepository,
    private val joinApplicationRepository: JoinApplicationRepository,
    private val donationApplicationRepository: DonationApplicationRepository,
    private val systemConfigService: SystemConfigService
) {
    fun sendJoinApplication(openId: String) {
        val userInfo = userInfoRepository.findByOpenid(openId) ?: throw BadRequestException("请先登录！")
        val joinApplication = joinApplicationRepository.findByUserInfoOpenid(openId)
            ?: throw BadRequestException("请先配置加入申请邮件的模版")
        val joinApplicationAddressee = systemConfigService[SystemConfig.Key.EmailRecipient.JOIN_APPLICATION_RECIPIENT]
        val (emailSubject, content) = generateJoinApplicationTemplate(joinApplication)
        send(joinApplicationAddressee, userInfo, emailSubject, content)
    }

    fun sendDonationApplication(openId: String) {
        val userInfo = userInfoRepository.findByOpenid(openId) ?: throw BadRequestException("请先登录！")
        val donationApplication = donationApplicationRepository.findByUserInfoOpenid(openId)
            ?: throw BadRequestException("请先配置加入申请邮件的模版")
        val donationApplicationAddressee =
            systemConfigService[SystemConfig.Key.EmailRecipient.DONATION_APPLICATION_RECIPIENT]
        val (emailSubject, content) = generateDonationApplicationTemplate(donationApplication)
        send(donationApplicationAddressee, userInfo, emailSubject, content)
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
