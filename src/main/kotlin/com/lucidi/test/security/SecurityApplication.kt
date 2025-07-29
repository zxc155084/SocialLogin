package com.lucidi.test.security

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@SecurityScheme(name = "login", scheme = "basic", type = SecuritySchemeType.HTTP, `in` = SecuritySchemeIn.HEADER)
class SecurityApplication

fun main(args: Array<String>) {
	runApplication<SecurityApplication>(*args)
}
