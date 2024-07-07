//package com.funnyjack.persistent.configuration
//
//import com.amazonaws.services.secretsmanager.AWSSecretsManager;
//import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
//import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.zaxxer.hikari.HikariConfig
//import com.zaxxer.hikari.HikariDataSource
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//class SecretManagerConfiguration(
//    @Value("\${aws.secretsmanager.secretName}")
//    private val secretName: String,
//    private val dataSourceProperties: DataSourceProperties,
//    private val objectMapper: ObjectMapper
//) {
//    @Bean
//    fun awsSecretsManager(): AWSSecretsManager {
//        return AWSSecretsManagerClientBuilder.defaultClient()
//    }
//
//    @Bean
//    fun dataSource(secretsManager: AWSSecretsManager): HikariDataSource {
//        val getSecretValueRequest = GetSecretValueRequest()
//            .withSecretId(secretName)
//        val getSecretValueResult = secretsManager.getSecretValue(getSecretValueRequest)
//        return objectMapper.readValue(getSecretValueResult.secretString, SecretValueResult::class.java).let {
//            val config = HikariConfig()
//            config.jdbcUrl = dataSourceProperties.url
//            config.username = dataSourceProperties.username?:it.username
//            config.password = dataSourceProperties.password?:it.password
//            HikariDataSource(config)
//        }
//    }
//
//    private class SecretValueResult(
//        val username: String,
//        val password: String
//    )
//}
