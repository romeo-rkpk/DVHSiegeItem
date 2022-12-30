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

tasks.register("단츄", Jar::class){
    group = "!danvhae"
    description = "의존성 모음집에 버전명 없이 파일을 내보냅니다"
    //dependsOn(listOf("jar", "build"))

    dependsOn("build")//.mustRunAfter("clean")

    from("build/classes/kotlin/main")
    from("build/resources/main")

    println("this is danchu task")
    doFirst{
        destinationDirectory.set(file(env.fetch("DEPEND_DIR")))
        archiveFileName.set(rootProject.name + ".jar")
    }

}

tasks.register("보라비", Jar::class){
    group = "!danvhae"
    description = "테스트 서버에 파일을 내보냅니다. 버전명 없이 내보냅니다."
    //dependsOn(listOf("jar", "build"))

    dependsOn("build")

    from("build/classes/kotlin/main")
    from("build/resources/main")

    println("this is borav task")
    doFirst{

        destinationDirectory.set(file(env.fetch("PLUGIN_DIR")))
        archiveFileName.set(rootProject.name + ".jar")

    }

}

tasks.register("해야", Jar::class) {
    group = "!danvhae"
    description = "어딘가에 있는 일시 폴더에 버전명을 포함하여 내보냅니다."
    //dependsOn(listOf("jar", "build"))
    //dependsOn("clean")
    dependsOn("build")//.mustRunAfter("clean")

    from("build/classes/kotlin/main")
    from("build/resources/main")

    println("this is haeya task")
    doFirst {
        destinationDirectory.set(file(env.fetch("ARCHIVE_DIR")))
    }
}