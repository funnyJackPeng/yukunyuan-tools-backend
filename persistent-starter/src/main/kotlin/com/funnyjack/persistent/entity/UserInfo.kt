package com.funnyjack.persistent.entity

import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType
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
    @Column(length = 256)
    var emailAuthCode: String? = null,

    @Enumerated(EnumType.STRING)
    @Column
    //https://www.baeldung.com/java-enums-jpa-postgresql
    @JdbcType(PostgreSQLEnumJdbcType::class)
    var emailCompany: Company? = null,
    @OneToOne(
        mappedBy = "userInfo",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val joinApplication: JoinApplication? = null
) {
    enum class Company(val host: String, val port: Int, val domain: String) {
        TENCENT("smtp.qq.com", 587, "@qq.com"),
        NETEASE("smtp.163.com", 25, "@163.com"),
    }
}

@Repository
interface UserInfoRepository : PagingAndSortingRepository<UserInfo, Long>, CrudRepository<UserInfo, Long> {
    fun findByOpenid(openid: String): UserInfo?
}
