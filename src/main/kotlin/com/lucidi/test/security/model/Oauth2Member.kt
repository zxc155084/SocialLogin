package com.lucidi.test.security.model

import jakarta.persistence.Entity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "`oauth2_member`")
data class Oauth2Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth2_member_id")
    var oauth2_member_id: Long? = null,

    var provider: String,
    var providerId: String,

    var name: String? = null,
    var email: String? = null,

    @Column(columnDefinition = "longText", name = "access_token")
    var accessToken: String? = null,

    var expiresAt: Date? = null,
)
