package com.lucidi.test.security.controllers

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("test")
@SecurityRequirement(name = "login")
class TestController {

    @GetMapping("hello")
    fun hello(): ResponseEntity<Any> {
        return ResponseEntity.ok("Hello World!!")
    }
}