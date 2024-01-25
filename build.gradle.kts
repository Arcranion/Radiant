plugins {
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.serialization") version "1.9.21"

    id("io.papermc.paperweight.userdev") version "1.5.11"

    id("com.github.johnrengelman.shadow") version "8.1.1"

    `java-library`
    `maven-publish`
}

group = "arcranion.radiant"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")

    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

    implementation("io.insert-koin:koin-core:3.5.3")
}

kotlin {
    jvmToolchain(17)
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            artifactId = "radiant"
            version = project.version as String

            from(components["java"])
        }
    }
}

tasks {
    shadowJar {
        dependencies {
            exclude(dependency("com.mojang:brigadier"))
        }
    }
}
