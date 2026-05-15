plugins {
    kotlin("jvm") version "2.3.21"
    kotlin("plugin.serialization") version "2.3.21"
    id("org.hildan.kotlin-publish") version "1.7.0"
    id("ru.vyarus.github-info") version "2.0.0"
    `maven-publish`
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

// publishing

github {
    user = "TheFruxz"
    license = "LGPLv3"
}

publishing {

    repositories {
        mavenLocal()
        maven("https://nexus.fruxz.dev/repository/releases/") {
            name = "fruxz.dev"
            credentials {
                username = System.getenv("FXZ_NEXUS_USER")
                password = System.getenv("FXZ_NEXUS_SECRET")
            }
        }

    }

}