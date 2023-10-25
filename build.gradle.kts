plugins {
    kotlin("jvm") version "1.9.20-RC2"
    id("com.github.ben-manes.versions") version "0.49.0"
}

repositories {
    mavenCentral()
//    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
//    implementation("com.github.ajalt.mordant:mordant:2.0.0-beta13")
//    implementation("org.jetbrains.kotlinx:multik-api:0.2.1")
//    implementation("org.jetbrains.kotlinx:multik-default:0.2.1")
//    implementation("io.arrow-kt:arrow-core:1.1.5")
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }
}
