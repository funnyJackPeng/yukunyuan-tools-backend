package com.funnyjack.monolith.model

import com.funnyjack.persistent.entity.UserInfo

data class UserInfoPatchModel(
    val localPart: String? = null,
    val emailCompany: UserInfo.Company? = null,
    val emailAuthCode: String? = null,
)

data class UserInfoViewModel(
    val id: Long,
    val openid: String,
    val userName: String,
    val sessionKey: String,
)

fun UserInfo.toViewModel() = UserInfoViewModel(
    id!!, openid, userName, sessionKey
)
