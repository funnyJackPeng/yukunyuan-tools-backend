package com.funnyjack.monolith.controller

import com.funnyjack.monolith.model.JoinApplicationCreationModel
import com.funnyjack.monolith.model.JoinApplicationPatchingModel
import com.funnyjack.monolith.model.JoinApplicationViewModel
import com.funnyjack.monolith.model.toViewModel
import com.funnyjack.monolith.service.JoinApplicationService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@Transactional(rollbackFor = [Throwable::class])
@RestController
@RequestMapping("/joinApplications")
class JoinApplicationController(
    private val joinApplicationService: JoinApplicationService
) {
    @GetMapping
    fun get(
        @RequestHeader("Authorization") openid: String
    ) = joinApplicationService.getByOpenId(openid).toViewModel()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody creationModel: JoinApplicationCreationModel,
        @RequestHeader("Authorization") openid: String
    ) = joinApplicationService.create(openid, creationModel).toViewModel()

    /**
     *   由于微信小程序官方不支持 patch 请求方式，所以只能改成 put
     *   https://developers.weixin.qq.com/miniprogram/dev/api/network/request/wx.request.html
     *   按照 RESTful 规范，部分修改的接口应该使用 patch
     */
    @PutMapping
    fun modify(
        @Valid @RequestBody patchingModel: JoinApplicationPatchingModel,
        @RequestHeader("Authorization") openid: String
    ): JoinApplicationViewModel {
        val joinApplication = joinApplicationService.getByOpenId(openid)
        return joinApplicationService.modify(joinApplication, patchingModel).toViewModel()
    }
}
