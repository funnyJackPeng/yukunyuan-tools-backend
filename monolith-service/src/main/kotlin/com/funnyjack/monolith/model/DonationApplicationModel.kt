package com.funnyjack.monolith.model

import com.funnyjack.persistent.entity.DonationApplication
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.math.BigDecimal

data class DonationApplicationCreationModel(
    val referrerNumber: String,
    val ownNumber: String,
    @field:Min(3000, message = "最少金额不能低于 3000")
    @field:Max(30000, message = "最大金额不能多于 30000")
    val amount: BigDecimal
)

data class DonationApplicationPatchingModel(
    val referrerNumber: String? = null,
    val ownNumber: String? = null,
    @field:Min(3000, message = "最少金额不能低于 3000")
    @field:Max(30000, message = "最大金额不能多于 30000")
    val amount: BigDecimal? = null
)

data class DonationApplicationViewModel(
    val referrerNumber: String,
    val ownNumber: String,
    val amount: BigDecimal
)

fun DonationApplication.toViewModel() = DonationApplicationViewModel(
    referrerNumber,
    ownNumber,
    amount
)
