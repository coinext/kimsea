import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object Versions {
    // Kotlin
    const val kotlinCoroutines = "1.3.7"
}

plugins {
    id("org.springframework.boot") version "2.6.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.jpa") version "1.6.0"

    id("org.gradlewebtools.minify") version "1.0.0"
}

apply(plugin = "io.spring.dependency-management")

group = "io.tommy.kimsea"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven {
        name = "jitpack.io"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${Versions.kotlinCoroutines}")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("mysql:mysql-connector-java")
    implementation("com.squareup.okhttp3:okhttp:4.4.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.github.spt-oss:thymeleaf-extras-minify:3.0.11.0")

    implementation("com.github.ipfs:java-ipfs-http-client:v1.3.3")
    implementation("commons-codec:commons-codec:1.15")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")

    implementation("org.web3j:core:5.0.0")
    implementation("org.web3j:contracts:5.0.0")
    implementation("org.web3j:abi:5.0.0")
    implementation("org.web3j:utils:5.0.0")

    implementation("io.sentry:sentry:5.5.2")
    implementation("io.sentry:sentry-logback:5.5.2")

    implementation("org.apache.tika:tika-core:2.2.1")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    implementation("com.google.firebase:firebase-admin:7.0.1")

    implementation("org.jsoup:jsoup:1.13.1")
    implementation("net.sourceforge.htmlunit:htmlunit:2.40.0")
    implementation("org.seleniumhq.selenium:selenium-java:3.141.59")
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:3.141.59")
    implementation("com.codeborne:phantomjsdriver:1.2.1")
}

minification {
    //https://github.com/gradle-webtools/gradle-minify-plugin
    js { //this: JsMinifyTask
        srcDir = project.file("src/main/resources/static/assets/js/")
        dstDir = project.file("src/main/resources/static/assets/dist/js/")

        options {
            compilationLevel = com.google.javascript.jscomp.CompilationLevel.SIMPLE_OPTIMIZATIONS
        }
    }
    css { //this: CssMinifyTask

        srcDir = project.file("src/main/resources/static/assets/css/")
        dstDir = project.file("src/main/resources/static/assets/dist/css/")

        options {
            simplifyCss = true
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {
    bootJar {
        dependsOn("jsMinify")

        enabled = true
        archiveFileName.set("kimsea-web.jar")
    }
    jar {
        enabled = false
    }
}