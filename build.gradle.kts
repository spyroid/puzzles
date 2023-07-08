import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    id("com.github.ben-manes.versions") version "0.47.0"
}

repositories {
    mavenCentral()
//    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
//    implementation("com.github.ajalt.mordant:mordant:2.0.0-beta13")
//    implementation("org.jetbrains.kotlinx:multik-api:0.2.1")
//    implementation("org.jetbrains.kotlinx:multik-default:0.2.1")
//    implementation("io.arrow-kt:arrow-core:1.1.5")
}

tasks {
    withType<Test>().all {
        allJvmArgs = listOf("--enable-preview")
        testLogging.showStandardStreams = true
        testLogging.showExceptions = true
        useJUnitPlatform {
        }
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "19"
        }
    }

    withType<JavaExec>().all {
        allJvmArgs = listOf("--enable-preview")
    }

    withType(JavaCompile::class.java).all {
        options.compilerArgs.addAll(listOf("--enable-preview", "-Xlint:preview"))
    }

    sourceSets {
        main {
            java.srcDirs("src")
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

