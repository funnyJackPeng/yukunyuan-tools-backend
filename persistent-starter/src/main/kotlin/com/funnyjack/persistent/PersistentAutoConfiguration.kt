package com.funnyjack.persistent

import com.funnyjack.persistent.entity.UserInfo
import com.funnyjack.persistent.entity.UserInfoRepository
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.config.BootstrapMode

@AutoConfiguration
@EntityScan(basePackageClasses = [UserInfo::class])
@EnableJpaRepositories(basePackageClasses = [UserInfoRepository::class], bootstrapMode = BootstrapMode.LAZY)
//TODO in fact, this annotation is not needed, only for IDEA to recognize the Repository correctly
@ComponentScan(basePackageClasses = [UserInfoRepository::class], lazyInit = true)
class PersistentAutoConfiguration
