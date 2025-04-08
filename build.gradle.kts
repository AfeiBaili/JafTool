plugins {
    kotlin("jvm") version "2.1.10"
//    kotlin("multiplatform") version "1.9.24"
}

group = "online.afeibaili"
version = properties["version"]!!

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(files("./lib/command-1.0.2.jar"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}