plugins {
    id("fabric-loom") version "1.7.4"
    id("maven-publish")
}

version = "1.0.0"
group = "com.example.addon"

repositories {
    mavenCentral()
    
    maven {
        name = "meteor-maven-snapshots"
        url = uri("https://maven.meteordev.org/snapshots")
    }
    maven {
        name = "meteor-maven-releases"
        url = uri("https://maven.meteordev.org/releases")
    }
    maven {
        name = "fabric"
        url = uri("https://maven.fabricmc.net/")
    }
    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings("net.fabricmc:yarn:1.21.1+build.1:v2")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.102.0+1.21.1")

    // Snapshot chuẩn cho 1.21.1 + ngắt tải phụ thuộc sâu
    modCompileOnly("meteordevelopment:meteor-client:0.5.9-SNAPSHOT") {
        isTransitive = false
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(21)
}
