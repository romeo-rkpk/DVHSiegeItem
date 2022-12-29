import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("co.uzzu.dotenv.gradle") version "2.0.0"
}

group = "com.danvhae.minecraft.siege.item"
version = "0.2.1"

repositories {
    mavenCentral()

    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }

    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven { url = uri("https://maven.enginehub.org/repo/" )}
    maven { url =uri("https://jitpack.io") }

    maven { url =uri("https://repo.dmulloy2.net/repository/public/") }
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")

    compileOnly(files(env.fetch("DVH_SIEGE_CORE")))
    compileOnly(files(env.fetch("MAGIC_SPELL")))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<ProcessResources>{
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml"){
        expand(project.properties)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}