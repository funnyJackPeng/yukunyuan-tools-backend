package com.funnyjack.monolith.service

import com.funnyjack.exception.ResourceNotFoundException
import com.funnyjack.monolith.model.JoinApplicationCreationModel
import com.funnyjack.monolith.model.JoinApplicationPatchingModel
import com.funnyjack.persistent.entity.JoinApplication
import com.funnyjack.persistent.entity.JoinApplicationRepository
import org.springframework.stereotype.Service

@Service
class JoinApplicationService(
    private val joinApplicationRepository: JoinApplicationRepository,
    private val userInfoService: UserInfoService
) {
    fun getByOpenId(openId: String) =
        joinApplicationRepository.findByUserInfoOpenid(openId)
            ?: throw ResourceNotFoundException(JoinApplication::class)

    fun create(openId: String, creationModel: JoinApplicationCreationModel): JoinApplication {
        val userInfo = userInfoService.get(openId)
        return JoinApplication(
            userInfo = userInfo,
            referrerNumber = creationModel.referrerNumber,
            ownNumber = creationModel.ownNumber,
            amount = creationModel.amount,
            surname = creationModel.surname,
            gender = creationModel.gender,
            nickName = creationModel.nickName,
            address = creationModel.address,
        ).save()
    }

    fun modify(joinApplication: JoinApplication, patchingModel: JoinApplicationPatchingModel): JoinApplication {
        return joinApplication.apply {
            patchingModel.referrerNumber?.also { referrerNumber = it }
            patchingModel.ownNumber?.also { ownNumber = it }
            patchingModel.amount?.also { amount = it }
            patchingModel.surname?.also { surname = it }
            patchingModel.gender?.also { gender = it }
            patchingModel.nickName?.also { nickName = it }
            patchingModel.address?.also { address = it }
        }.save()
    }

    private fun JoinApplication.save() = joinApplicationRepository.save(this)
}