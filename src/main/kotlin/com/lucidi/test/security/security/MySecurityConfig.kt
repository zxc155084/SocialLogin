package com.lucidi.test.security.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class MySecurityConfig(
    private val myOAuth2UserService: MyOAuth2UserService,
    private val myOidUserService: MyOidUserService,
) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it.requestMatchers("/actuator/health").permitAll()//忽略
                    .anyRequest().authenticated()//全部驗證
            }
            // 表單登入
            .formLogin(Customizer.withDefaults())


            // OAuth2 社交登入
            //.oauth2Login(Customizer.withDefaults())

            // OAuth2 社交登入 加上 記錄 登入者資訊User
            .oauth2Login {
                it.userInfoEndpoint { infoEndpoint ->
                    infoEndpoint.userService(myOAuth2UserService)
                    infoEndpoint.oidcUserService(myOidUserService)
                }
            }

        return http.build()
    }

    @Bean
    @Throws(Exception::class)
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer {
            it.ignoring().requestMatchers(
                "/lucidi/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/h2-console/**",
                "/"
            )
        }
    }
}