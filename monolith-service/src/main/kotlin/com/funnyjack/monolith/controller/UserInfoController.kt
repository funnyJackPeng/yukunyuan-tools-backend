package com.funnyjack.monolith.controller

import com.funnyjack.monolith.model.UserInfoPatchModel
import com.funnyjack.monolith.model.UserInfoViewModel
import com.funnyjack.monolith.model.toViewModel
import com.funnyjack.monolith.service.UserInfoService
import jakarta.validation.Valid
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

    @PutMapping
    fun modify(
        @Valid @RequestBody patchingModel: UserInfoPatchModel,
        @RequestHeader("Authorization") openid: String
    ): UserInfoViewModel {
        val userInfo = userInfoService.get(openid)
        return userInfoService.modifyEmail(userInfo, patchingModel).toViewModel()
    }

    @PostMapping("/login")
    fun login(
        @RequestParam jsCode: String,
        @RequestParam userName: String,
    ): UserInfoViewModel {
        return userInfoService.login(jsCode, userName).toViewModel()
    }
}
