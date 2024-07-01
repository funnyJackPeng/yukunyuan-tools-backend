package com.funnyjack.email.controller

import com.funnyjack.email.service.EmailService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/emails")
class EmailController(
    private val emailService: EmailService
) {
    @PostMapping("/joinApplication/send")
    fun sendJoinApplication(
        @RequestHeader("Authorization") openid: String
    ) {
        emailService.sendJoinApplication(openid)
    }
}
