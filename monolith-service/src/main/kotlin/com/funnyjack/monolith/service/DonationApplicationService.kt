package com.funnyjack.monolith.service

import com.funnyjack.exception.ResourceNotFoundException
import com.funnyjack.monolith.model.DonationApplicationCreationModel
import com.funnyjack.monolith.model.DonationApplicationPatchingModel
import com.funnyjack.persistent.entity.DonationApplication
import com.funnyjack.persistent.entity.DonationApplicationRepository
import org.springframework.stereotype.Service

@Service
class DonationApplicationService(
    private val donationApplicationRepository: DonationApplicationRepository,
    private val userInfoService: UserInfoService
) {
    fun getByOpenId(openId: String) =
        donationApplicationRepository.findByUserInfoOpenid(openId)
            ?: throw ResourceNotFoundException(DonationApplication::class)

    fun create(openId: String, creationModel: DonationApplicationCreationModel): DonationApplication {
        val userInfo = userInfoService.get(openId)
        return DonationApplication(
            userInfo = userInfo,
            referrerNumber = creationModel.referrerNumber,
            ownNumber = creationModel.ownNumber,
            amount = creationModel.amount,
        ).save()
    }

    fun modify(
        donationApplication: DonationApplication,
        patchingModel: DonationApplicationPatchingModel
    ): DonationApplication {
        return donationApplication.apply {
            patchingModel.referrerNumber?.also { referrerNumber = it }
            patchingModel.ownNumber?.also { ownNumber = it }
            patchingModel.amount?.also { amount = it }
        }.save()
    }

    private fun DonationApplication.save() = donationApplicationRepository.save(this)
}
