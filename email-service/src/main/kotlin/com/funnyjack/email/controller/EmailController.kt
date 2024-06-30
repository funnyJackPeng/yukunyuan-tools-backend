package com.funnyjack.email.controller

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/email")
class EmailController(
//    private val javaMailSender: JavaMailSender,
//    private val mailProperties: MailProperties
) {
    @PostMapping("/send")
    fun sendEmailTest() {
        SimpleMailMessage().apply {
            setTo("554517318@qq.com")
            from = "13388883711@163.com"
            subject = "Test subject"
            text = "测试通过 spring boot 用 163 邮箱发送邮件，代码中配置 property"
        }.let {
            JavaMailSenderImpl().apply {
                host = "smtp.163.com"
                port = 25
                username = "13388883711@163.com"
                password = "ROEGRFUFXGBFJAAR"
            }.send(it)
        }
    }
}
