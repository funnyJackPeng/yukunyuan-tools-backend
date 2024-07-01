package com.funnyjack.persistent.service

import com.funnyjack.common.string.fullTrim
import com.funnyjack.persistent.entity.SystemConfig
import com.funnyjack.persistent.entity.SystemConfigRepository
import org.springframework.stereotype.Service

@Service
class SystemConfigService(
    private val systemConfigRepository: SystemConfigRepository
) {
    operator fun get(key: String) = systemConfigRepository.findByKey(key.fullTrim())?.value ?: ""

    operator fun set(key: String, value: String): SystemConfig {
        val formattedKey = key.fullTrim()
        require(formattedKey.isNotBlank())
        return systemConfigRepository.save(SystemConfig(formattedKey, value.fullTrim()))
    }

    fun getAllConfigs() = systemConfigRepository.findAll().associate { it.key to it.value }

}
