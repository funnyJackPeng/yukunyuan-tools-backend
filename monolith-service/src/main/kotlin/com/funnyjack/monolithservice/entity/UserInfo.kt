package com.funnyjack.monolithservice.entity

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
    @Column(nullable = false)
    val sessionKey: String
)

@Repository
interface UserInfoRepository : PagingAndSortingRepository<UserInfo, Long>, CrudRepository<UserInfo, Long>