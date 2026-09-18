plugins {
    id("fabric-loom") version "1.7.4"
    id("maven-publish")
}

version = "1.0.0"
group = "com.example.addon"

repositories {
    mavenCentral()
    maven {
        name = "meteor-dev"
        url = uri("https://maven.meteordevelopment.meteorclient.com/releases")
    }
    maven {
        name = "meteor-snapshots"
        url = uri("https://maven.meteordevelopment.meteorclient.com/snapshots")
    }
    maven {
        name = "fabric"
        url = uri("https://maven.fabricmc.net/")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings("net.fabricmc:yarn:1.21.1+build.1:v2")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.102.0+1.21.1")

    modImplementation("meteordevelopment:meteor-client:0.5.8-1.21.1")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(21)
}
