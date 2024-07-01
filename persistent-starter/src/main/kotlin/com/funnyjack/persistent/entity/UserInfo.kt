package com.funnyjack.persistent.entity

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Entity
class UserInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val openid: String,
    @Column(length = 256, nullable = false)
    var userName: String,
    @Column(nullable = false)
    var sessionKey: String,
    @Column(length = 256)
    var localPart: String? = null,
    @Column
    var emailCompany: EmailCompany? = null,
    @Column(length = 256)
    var emailAuthCode: String? = null,
    @OneToOne(
        mappedBy = "userInfo",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val joinApplication: JoinApplication? = null
) {
    enum class EmailCompany(val host: String, val port: Int, val domain: String) {
        TECENT("smtp.qq.com", 587, "@qq.com"),
        NETEASE("smtp.163.com", 25, "@163.com"),
    }
}

@Repository
interface UserInfoRepository : PagingAndSortingRepository<UserInfo, Long>, CrudRepository<UserInfo, Long> {
    fun findByOpenid(openid: String): UserInfo?
}
