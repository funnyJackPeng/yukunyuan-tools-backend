package com.funnyjack.monolith.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.funnyjack.constant.LoginConstant
import com.funnyjack.exception.ResourceNotFoundException
import com.funnyjack.monolith.model.LoginResponseModel
import com.funnyjack.monolith.model.UserInfoPatchModel
import com.funnyjack.persistent.entity.UserInfo
import com.funnyjack.persistent.entity.UserInfoRepository
import com.hiczp.spring.error.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class UserInfoService(
    private val userInfoRepository: UserInfoRepository,
    private val objectMapper: ObjectMapper
) {
    fun get(openId: String) =
        userInfoRepository.findByOpenid(openId) ?: throw ResourceNotFoundException(UserInfo::class)

    fun login(jsCode: String, userName: String): UserInfo {
        val restTemplate = RestTemplate()
        val uri = UriComponentsBuilder.fromHttpUrl(LoginConstant.LOGIN_PATH).apply {
            queryParam("appid", LoginConstant.APPID)
            queryParam("secret", LoginConstant.SECRET)
            queryParam("grant_type", LoginConstant.AUTHORIZATION_CODE)
            queryParam("js_code", jsCode)
        }.toUriString()
        val result = restTemplate.getForObject(uri, String::class.java).let {
            objectMapper.readValue(it, LoginResponseModel::class.java)
        }
        if (result.errcode != null) {
            throw BadRequestException("login failed, error code: ${result.errcode}, error message: ${result.errmsg}")
        }
        return userInfoRepository.findByOpenid(result.openid!!)?.let {
            it.apply {
                this.sessionKey = result.session_key!!
                this.userName = userName
            }
        } ?: UserInfo(
            openid = result.openid,
            sessionKey = result.session_key!!,
            userName = userName
        ).save()
    }

    fun modifyEmail(userInfo: UserInfo, patchModel: UserInfoPatchModel) {
        userInfo.apply {
            patchModel.localPart?.also { localPart = it }
            patchModel.emailCompany?.also { emailCompany = it }
            patchModel.emailAuthCode?.also { emailAuthCode = it }
        }
    }

    private fun UserInfo.save() = userInfoRepository.save(this)
}
