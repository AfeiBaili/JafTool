plugins {
    kotlin("jvm") version "2.1.10"
}

group = "online.afeibaili"
version = properties["version"]!!

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(files("./lib/command-1.1.0.jar"))
}



tasks.jar {
    manifest {
        attributes["Main-Class"] = "online.afeibaili.MainKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}