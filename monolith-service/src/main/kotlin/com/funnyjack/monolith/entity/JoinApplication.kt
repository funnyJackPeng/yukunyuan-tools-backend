package com.funnyjack.monolith.entity

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Entity
class JoinApplication(
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
    var amount: BigDecimal,
    @Column(length = 3, nullable = false)
    var surname: String,
    @Column(length = 3, nullable = false)
    var gender: String,
    @Column(length = 256, nullable = false)
    var nickName: String,
    @Column(length = 512, nullable = false)
    var address: String,
)

@Repository
interface JoinApplicationRepository : CrudRepository<JoinApplication, Long> {
    fun findByUserInfoOpenid(openid: String): JoinApplication?
}
