package com.funnyjack.monolith.model

import com.funnyjack.monolith.entity.JoinApplication
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.math.BigDecimal

data class JoinApplicationCreationModel(
    val referrerNumber: String,
    val ownNumber: String,
    @field:Min(3000, message = "最少金额不能低于 3000")
    @field:Max(30000, message = "最大金额不能多于 30000")
    val amount: BigDecimal,
    val surname: String,
    val gender: String,
    val nickName: String,
    val address: String
)

data class JoinApplicationPatchingModel(
    val referrerNumber: String? = null,
    val ownNumber: String? = null,
    @field:Min(3000, message = "最少金额不能低于 3000")
    @field:Max(30000, message = "最大金额不能多于 30000")
    val amount: BigDecimal? = null,
    val surname: String? = null,
    val gender: String? = null,
    val nickName: String? = null,
    val address: String? = null
)

data class JoinApplicationViewModel(
    val referrerNumber: String,
    val ownNumber: String,
    val amount: BigDecimal,
    val surname: String,
    val gender: String,
    val nickName: String,
    val address: String,
)

fun JoinApplication.toViewModel() = JoinApplicationViewModel(
    referrerNumber,
    ownNumber,
    amount,
    surname,
    gender,
    nickName,
    address
)
