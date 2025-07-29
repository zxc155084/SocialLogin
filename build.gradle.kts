plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.0"
}

group = "com.lucidi.test"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// security
	implementation("org.springframework.boot:spring-boot-starter-security")

	// oauth2
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

	runtimeOnly("com.h2database:h2")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Spring Boot + Swagger 3
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5"){
		exclude(group = "org.apache.commons", module = "commons-lang3")
	}
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.8.5")

	// https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
	implementation("org.apache.commons:commons-lang3:3.18.0")

	// spring-boot-starter-data-jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
