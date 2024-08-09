package com.funnyjack.persistent.entity

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Entity
class DonationApplication(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userInfo_id", nullable = false, updatable = false)
    val userInfo: UserInfo,
    @Column(length = 256, nullable = false)
    var referrerNumber: String,
    @Column(length = 256, nullable = false)
    var ownNumber: String,
    @Column(nullable = false)
    var amount: BigDecimal
)

@Repository
interface DonationApplicationRepository : CrudRepository<DonationApplication, Long> {
    fun findByUserInfoOpenid(openid: String): DonationApplication?
}
