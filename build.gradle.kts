plugins {
    kotlin("jvm") version "2.3.20"
    kotlin("plugin.serialization") version "2.3.20"
}

group = "dev.fruxz"
version = "1.0-SNAPSHOT"

val ktor_version = "3.4.2"

repositories {
    mavenCentral()
    maven("https://nexus.fruxz.dev/repository/public/")
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("dev.fruxz:ascend:2026.4-b59e13c")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0-rc01")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")

    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.12.0")

    implementation(platform("io.ktor:ktor-bom:$ktor_version"))
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-client-core")
    implementation("io.ktor:ktor-client-cio")
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}