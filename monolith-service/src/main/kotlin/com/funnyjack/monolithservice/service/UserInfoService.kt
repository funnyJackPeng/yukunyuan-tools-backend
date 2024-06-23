package com.funnyjack.monolithservice.service

import LoginConstant
import com.fasterxml.jackson.databind.ObjectMapper
import com.funnyjack.monolithservice.entity.UserInfo
import com.funnyjack.monolithservice.entity.UserInfoRepository
import com.funnyjack.monolithservice.model.LoginResponseModel
import com.funnyjack.testdeploy.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class UserInfoService(
    private val userInfoRepository: UserInfoRepository,
    private val objectMapper: ObjectMapper
) {
    fun get(id: Long) = userInfoRepository.findById(id) ?: throw ResourceNotFoundException(UserInfo::class)

    fun login(jsCode: String): LoginResponseModel {
        val restTemplate = RestTemplate()
        val uri = UriComponentsBuilder.fromHttpUrl(LoginConstant.LOGIN_PATH).apply {
            queryParam("appid", LoginConstant.APPID)
            queryParam("secret", LoginConstant.SECRET)
            queryParam("grant_type", LoginConstant.AUTHORIZATION_CODE)
            queryParam("jsCode", jsCode)
        }.toUriString()
        val result = restTemplate.getForObject(uri, String::class.java)
        return objectMapper.readValue(result, LoginResponseModel::class.java)
    }
}
