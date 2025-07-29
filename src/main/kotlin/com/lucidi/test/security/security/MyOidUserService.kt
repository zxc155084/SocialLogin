package com.lucidi.test.security.security

import com.lucidi.test.security.model.Oauth2Member
import com.lucidi.test.security.repository.Oauth2MemberRepository
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Component
import java.util.*

/**
 * 用來處理 OAuth2 + OpenID Connect 的用戶資訊 (ex: Google)
 */
@Component
class MyOidUserService(
    private val oauth2MemberRepository: Oauth2MemberRepository,
) : OidcUserService() {

    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OidcUserRequest): OidcUser {

        val oidcUser = super.loadUser(userRequest)

        // 取得 oAuth2User 和 oAuth2UserRequest 中的資訊
        val email = Objects.toString(oidcUser.getAttributes().get("email"), null)
        val name = Objects.toString(oidcUser.getAttributes().get("name"), null)

        val provider = userRequest.clientRegistration.registrationId
        val providerId = oidcUser.name

        val accessToken = userRequest.accessToken.tokenValue
        val expiresAt = Date.from(userRequest.accessToken.expiresAt)

        // 從資料庫查詢此 provider + providerId 組合的 oauth2 member 是否存在
        val oAuth2Member = oauth2MemberRepository.getOAuth2MemberByProviderAndProviderId(provider, providerId)

        // 如果 oauth2 member 不存在，就創建一個新的 member
        if (oAuth2Member == null) {
            oauth2MemberRepository.save(
                Oauth2Member(
                    provider = provider,
                    providerId = providerId,
                    name = name,
                    email = email,
                    accessToken = accessToken,
                    expiresAt = expiresAt
                )
            )
        }

        // 返回 Spring Security 原本的 oidcUser
        return oidcUser
    }
}