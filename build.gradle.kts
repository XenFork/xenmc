plugins {
    `maven-publish`
}

val projectVersion: String by rootProject

version = projectVersion

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    layout.buildDirectory.set(rootProject.file(".gradle/cache/$name"))
//    setBuildDir(".gradle/cache/$name")

    version = rootProject.findProperty("${project.name}Version") ?: projectVersion

    tasks.withType<Jar> {
        archiveBaseName = "${project.name}-xenmc"
    }

    publishing {
        repositories {
            maven {
                url = rootProject.uri(".gradle/build/maven")
            }
        }
        publications {
            create<MavenPublication>("maven_${project.name}") {
                groupId = group.toString()
                artifactId = "xenmc-${project.name}"
                version = project.version.toString()
                from(components["java"])
            }
        }
    }
}

allprojects {
    group = "io.github.xenfork.xenmc"

    repositories {
        mavenCentral()
        mavenLocal()

        maven {
            url = rootProject.uri(".gradle/build/maven")
        }
    }
}
