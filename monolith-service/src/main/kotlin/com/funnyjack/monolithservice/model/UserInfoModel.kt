package com.funnyjack.monolithservice.model

import com.funnyjack.monolithservice.entity.UserInfo

class UserInfoViewModel(
    val id: Long,
    val openid: String,
    val sessionKey: String,
)

fun UserInfo.toViewModel() = UserInfoViewModel(
    id!!, openid, sessionKey
)