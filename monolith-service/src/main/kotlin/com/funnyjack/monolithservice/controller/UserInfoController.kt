package com.funnyjack.monolithservice.controller

import com.funnyjack.monolithservice.model.LoginResponseModel
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
    @GetMapping("/{id}")
    fun get(
        @PathVariable id: Long
    ): UserInfoViewModel {
        return userInfoService.get(id).get().toViewModel()
    }

    @PostMapping("/login")
    fun login(
        @RequestParam jsCode: String,
    ): LoginResponseModel {
        return userInfoService.login(jsCode)
    }
}
