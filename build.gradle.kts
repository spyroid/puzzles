import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    java
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.github.ajalt.mordant:mordant:2.0.0-beta5")
//    implementation("org.jetbrains.kotlinx:multik-api:0.1.1")
//    implementation("org.jetbrains.kotlinx:multik-default:0.1.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
//        freeCompilerArgs = listOf("-Xcontext-receivers")
//        jvmTarget = "18"
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
}

java {
//    sourceCompatibility = JavaVersion.VERSION_18
//    targetCompatibility = JavaVersion.VERSION_19
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }
}
