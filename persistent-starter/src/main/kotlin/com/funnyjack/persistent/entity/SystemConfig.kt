package com.funnyjack.persistent.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Entity
class SystemConfig(
    @Id
    @Column(length = 256, nullable = false)
    var key: String,

    @Column(length = 256, nullable = false)
    var value: String,
) {
    object Key {
        object EmailRecipient {
            const val JOIN_APPLICATION_RECIPIENT = "emailRecipient.joinApplicationRecipient"
            const val DONATION_APPLICATION_RECIPIENT = "emailRecipient.donationApplicationRecipient"
        }
    }
}

@Repository
interface SystemConfigRepository : CrudRepository<SystemConfig, String> {
    fun findByKey(key: String): SystemConfig?
}
