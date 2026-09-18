plugins {
    id 'fabric-loom' version '1.7-SNAPSHOT'
    id 'maven-publish'
}

version = "1.0.0"
group = "com.example.addon"

repositories {
    mavenCentral()
    maven { name = "meteor-dev"; url = "https://maven.meteordevelopment.meteorclient.com/releases" }
    maven { name = "meteor-snapshots"; url = "https://maven.meteordevelopment.meteorclient.com/snapshots" }
}

dependencies {
    minecraft "com.mojang:minecraft:1.21.1"
    mappings "net.fabricmc:yarn:1.21.1+build.1:v2"
    fabricApi "net.fabricmc.fabric-api:fabric-api:0.102.0+1.21.1"

    modImplementation "meteordevelopment:meteor-client:0.5.8-1.21.1"
}
