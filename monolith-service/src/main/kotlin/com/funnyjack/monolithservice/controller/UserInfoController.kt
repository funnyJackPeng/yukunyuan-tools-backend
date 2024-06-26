package com.funnyjack.monolithservice.controller

import com.funnyjack.monolithservice.model.UserInfoViewModel
import com.funnyjack.monolithservice.model.toViewModel
import com.funnyjack.monolithservice.service.UserInfoService
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@Transactional(rollbackFor = [Throwable::class])
@RestController
@RequestMapping("/userInfos")
class UserInfoController(
    private val userInfoService: UserInfoService
) {
    @GetMapping("/{openId}")
    fun get(
        @PathVariable openId: String
    ): UserInfoViewModel {
        return userInfoService.get(openId).toViewModel()
    }

    @PostMapping("/login")
    fun login(
        @RequestParam jsCode: String,
        @RequestParam userName: String,
    ): UserInfoViewModel {
        return userInfoService.login(jsCode, userName).toViewModel()
    }
}
