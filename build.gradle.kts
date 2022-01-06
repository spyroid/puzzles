import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("com.github.ajalt.mordant:mordant:2.0.0-beta2")
//    implementation("org.jetbrains.kotlinx:multik-api:0.1.1")
//    implementation("org.jetbrains.kotlinx:multik-default:0.1.1")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }
}
