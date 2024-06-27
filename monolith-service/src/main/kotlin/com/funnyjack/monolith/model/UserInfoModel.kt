package com.funnyjack.monolith.model

import com.funnyjack.monolith.entity.UserInfo

class UserInfoViewModel(
    val id: Long,
    val openid: String,
    val userName: String,
    val sessionKey: String,
)

fun UserInfo.toViewModel() = UserInfoViewModel(
    id!!, openid, userName, sessionKey
)