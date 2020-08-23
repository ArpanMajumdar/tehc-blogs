import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
}

group = "com.github.arpan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {

    implementation(group = "org.slf4j", name = "slf4j-simple", version = "1.7.30")
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-runtime", version = "1.0-M1-1.4.0-rc")
    implementation(group = "org.apache.kafka", name = "kafka-streams", version = "2.6.0")
    testImplementation(kotlin("test-junit5"))
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}