package com.funnyjack.monolith.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.funnyjack.monolith.constant.LoginConstant
import com.funnyjack.monolith.entity.UserInfo
import com.funnyjack.monolith.entity.UserInfoRepository
import com.funnyjack.monolith.model.LoginResponseModel
import com.funnyjack.testdeploy.exception.ResourceNotFoundException
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

    private fun UserInfo.save() = userInfoRepository.save(this)
}
