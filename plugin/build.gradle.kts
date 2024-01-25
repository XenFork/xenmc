plugins {
    `java-gradle-plugin`
}

val authors: String by rootProject

dependencies {

    implementation(gradleApi())
    implementation(libs.bundles.xenmc.gradle)//御用反射等公共库
    implementation(libs.bundles.asm.all)
//    implementation(libs.proguard.gradle)
}

gradlePlugin {
    plugins {
        create("xenmcPlugin") {
            id = "io.github.xenfork.xenmc.plugin"
            implementationClass = "io.github.xenfork.xenmc.gradle.XenProject"
        }
    }
}

tasks.withType<Jar> {
    enabled = true
    isZip64 = true
}

tasks.register<Jar>("sourcesJar") {
    dependsOn(tasks["classes"])
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

tasks.getByName<ProcessResources>("processResources") {
    inputs.property("version", project.version)
    inputs.property("authors", authors)
    filesMatching("info.json") {
        expand(
            "version" to project.version,
            "authors" to authors,
        )
    }
}

java {
    withSourcesJar()
}
