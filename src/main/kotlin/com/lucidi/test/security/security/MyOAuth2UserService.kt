package com.lucidi.test.security.security

import com.lucidi.test.security.model.Oauth2Member
import com.lucidi.test.security.repository.Oauth2MemberRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import java.util.Date
import java.util.Objects

/**
 * 用來處理 OAuth2 + OpenID Connect 的用戶資訊 (ex: facebook、GitHub、LINE)
 */
@Component
class MyOAuth2UserService(
    private val oauth2MemberRepository: Oauth2MemberRepository,
) : DefaultOAuth2UserService() {

    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {

        val oAuth2User = super.loadUser(userRequest)

        // 取得 oAuth2User 和 oAuth2UserRequest 中的資訊
        val email = Objects.toString(oAuth2User.getAttributes().get("email"), null)
        val name = Objects.toString(oAuth2User.getAttributes().get("name"), null)

        val provider = userRequest.clientRegistration.registrationId
        val providerId = oAuth2User.name

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

        // 返回 Spring Security 原本的 OAuth2User
        return oAuth2User
    }
}