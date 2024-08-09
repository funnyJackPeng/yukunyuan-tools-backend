package com.funnyjack.monolith.controller

import com.funnyjack.monolith.model.DonationApplicationCreationModel
import com.funnyjack.monolith.model.DonationApplicationPatchingModel
import com.funnyjack.monolith.model.DonationApplicationViewModel
import com.funnyjack.monolith.model.toViewModel
import com.funnyjack.monolith.service.DonationApplicationService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@Transactional(rollbackFor = [Throwable::class])
@RestController
@RequestMapping("/donationApplications")
class DonationApplicationController(
    private val donationApplicationService: DonationApplicationService
) {
    @GetMapping
    fun get(
        @RequestHeader("Authorization") openid: String
    ) = donationApplicationService.getByOpenId(openid).toViewModel()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody creationModel: DonationApplicationCreationModel,
        @RequestHeader("Authorization") openid: String
    ) = donationApplicationService.create(openid, creationModel).toViewModel()

    /**
     *   由于微信小程序官方不支持 patch 请求方式，所以只能改成 put
     *   https://developers.weixin.qq.com/miniprogram/dev/api/network/request/wx.request.html
     *   按照 RESTful 规范，部分修改的接口应该使用 patch
     */
    @PutMapping
    fun modify(
        @Valid @RequestBody patchingModel: DonationApplicationPatchingModel,
        @RequestHeader("Authorization") openid: String
    ): DonationApplicationViewModel {
        val donationApplication = donationApplicationService.getByOpenId(openid)
        return donationApplicationService.modify(donationApplication, patchingModel).toViewModel()
    }
}
