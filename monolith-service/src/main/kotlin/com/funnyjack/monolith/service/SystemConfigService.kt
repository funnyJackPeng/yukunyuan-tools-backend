package com.funnyjack.monolith.service

import com.funnyjack.exception.ResourceNotFoundException
import com.funnyjack.persistent.entity.SystemConfig
import com.funnyjack.persistent.entity.SystemConfigRepository
import org.springframework.stereotype.Service

@Service
class SystemConfigService(
    private val systemConfigRepository: SystemConfigRepository
) {
    fun get(id: Long): SystemConfig = systemConfigRepository.findById(id).orElseThrow {
        ResourceNotFoundException(SystemConfig::class)
    }

    fun list(): List<SystemConfig> = systemConfigRepository.findAll()
}
