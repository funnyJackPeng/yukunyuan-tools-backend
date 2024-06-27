package com.funnyjack.email.controller

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/email")
class EmailController(
    private val javaMailSender: JavaMailSender
) {
    @PostMapping("/send")
    fun sendEmailTest() {
        SimpleMailMessage().apply {
            setTo("554517318@qq.com")
            from = "13388883711@163.com"
            subject = "Test subject"
            text = "测试通过 spring boot 用 163 邮箱发送邮件"
        }.let {
            javaMailSender.send(it)
        }
    }
}
