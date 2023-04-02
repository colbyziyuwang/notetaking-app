import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.6.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

group = "net.codebot"
version = "1.0.0"

val compileKotlin: KotlinCompile by tasks
val compileJava: JavaCompile by tasks
compileJava.destinationDirectory.set(compileKotlin.destinationDirectory)

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":shared"))
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainModule.set("console")
    mainClass.set("net.codebot.console.MainKt")
}