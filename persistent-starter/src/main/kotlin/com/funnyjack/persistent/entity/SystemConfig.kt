package com.funnyjack.persistent.entity

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Entity
class SystemConfig(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(length = 256, nullable = false)
    var joinApplicationAddressee: String,
)

@Repository
interface SystemConfigRepository : JpaRepository<SystemConfig, Long>
