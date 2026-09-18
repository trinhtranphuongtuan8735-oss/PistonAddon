plugins {
    id("fabric-loom") version "1.7.4"
    id("maven-publish")
}

version = "1.0.0"
group = "com.example.addon"

repositories {
    mavenCentral()
    // Khai báo Jitpack ở trên cùng để ưu tiên tìm kiếm
    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
    maven {
        name = "fabric"
        url = uri("https://maven.fabricmc.net/")
    }
    // Maven chính chủ của Meteor (dự phòng)
    maven {
        name = "meteor-dev"
        url = uri("https://maven.meteordevelopment.meteorclient.com/releases")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings("net.fabricmc:yarn:1.21.1+build.1:v2")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.102.0+1.21.1")

    // Cấu hình dependency Meteor qua Jitpack chuẩn format
    // Format Jitpack: com.github.User:Repo:Tag/Commit
    modCompileOnly("com.github.MeteorDevelopment:meteor-client:0.5.8") {
        isTransitive = false
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(21)
}
