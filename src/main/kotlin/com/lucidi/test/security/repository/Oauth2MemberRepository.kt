package com.lucidi.test.security.repository

import com.lucidi.test.security.model.Oauth2Member
import org.springframework.data.jpa.repository.JpaRepository

interface Oauth2MemberRepository : JpaRepository<Oauth2Member, Long> {

    fun getOAuth2MemberByProviderAndProviderId(provider: String, providerId: String): Oauth2Member?
}