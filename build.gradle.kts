import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.3.0-Beta2"
    id("com.github.ben-manes.versions") version "0.53.0"
}

repositories {
    mavenCentral()
//    maven { url = uri("https://jitpack.io") }
}

dependencies {
//    implementation("com.moriatsushi.cacheable:cacheable-core:0.0.2")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
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
    processResources {
        enabled = false
    }
    processTestResources {
        enabled = false
    }
    jar {
        enabled = false
    }
    test {
        enabled = false
    }
    compileJava {
//        options.release = 22
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-opt-in=kotlin.RequiresOptIn")
//        jvmTarget.set(JvmTarget.JVM_22)
    }
}
