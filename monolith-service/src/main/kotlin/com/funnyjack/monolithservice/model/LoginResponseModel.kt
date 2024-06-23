package com.funnyjack.monolithservice.model

data class LoginResponseModel(
    val openid: String?,
    val sessionKey: String?,
    val unionid: String?,
    val errcode: String,
    val errmsg: String,
)
