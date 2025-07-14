import com.sogeor.framework.gradle.loadProperty
import java.net.HttpURLConnection
import java.net.URI
import java.util.*

plugins {
    `java-platform`
    `maven-publish`
    signing
}

description = "Предоставляет BOM модульного многофункционального фреймворка."
group = "com.sogeor.framework"
version = "1.0.0-SNAPSHOT"

dependencies {
    constraints {
        api(project(":annotation"))
        api(project(":collection"))
        api(project(":common"))
        api(project(":function"))
        api(project(":throwable"))
        api(project(":validation"))
    }
}

publishing {
    publications {
        create<MavenPublication>("platform") {
            artifactId = "bom"

            from(components["javaPlatform"])

            pom {
                name = artifactId
                description = project.description
                url = "https://github.com/sogeor/framework"
            }
        }
    }
}

tasks.wrapper {
    gradleVersion = "8.14"
}

val isSnapshotVersion = "-SNAPSHOT" in version.toString()
val sonatypeUsername = loadProperty("SONATYPE_CREDENTIALS_USERNAME", rootProject)
val sonatypePassword = loadProperty("SONATYPE_CREDENTIALS_PASSWORD", rootProject)

allprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    publishing {
        repositories {
            maven {
                name = "sonatype"
                url = uri(
                    if (isSnapshotVersion) "https://central.sonatype.com/repository/maven-snapshots/"
                    else "https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/"
                )

                credentials {
                    username = sonatypeUsername
                    password = sonatypePassword
                }
            }
        }

        publications {
            withType<MavenPublication> {
                pom {
                    description = project.description
                    url = "https://github.com/sogeor/framework"
                    inceptionYear = "2025"

                    licenses {
                        license {
                            name = "Apache License, Version 2.0"
                            url = "https://www.apache.org/licenses/LICENSE-2.0"
                            distribution = "repo"
                        }
                    }

                    organization {
                        name = "Sogeor"
                        url = "https://github.com/sogeor"
                    }

                    developers {
                        developer {
                            id = "Bloogefest"
                            name = "George Sopin"
                            url = "https://github.com/Bloogefest"
                            organization = "Sogeor"
                            organizationUrl = "https://github.com/sogeor"
                            timezone = "Europe/Moscow"
                        }
                    }

                    scm {
                        connection = "scm:git:git://github.com/sogeor/framework.git"
                        developerConnection = "scm:git:ssh://github.com:sogeor/framework.git"
                        url = "https://github.com/sogeor/framework"
                    }

                    issueManagement {
                        system = "Github"
                        url = "https://github.com/sogeor/framework/issues"
                    }

                    ciManagement {
                        system = "Github"
                        url = "https://github.com/sogeor/framework/actions"
                    }
                }
            }
        }
    }

    signing {
        useInMemoryPgpKeys(
            loadProperty("SINGING_KEY_ID", rootProject),
            loadProperty("SINGING_KEY_SECRET", rootProject),
            loadProperty("SINGING_KEY_PASSWORD", rootProject)
        )

        sign(publishing.publications)
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")

    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenLocal()
        mavenCentral()
    }

    configure<JavaPluginExtension> {
        withJavadocJar()
        withSourcesJar()

        modularity.inferModulePath = true

        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    if (name !in arrayOf("annotation", "throwable")) tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<Javadoc> {
        val options = options as CoreJavadocOptions
        options.encoding = "UTF-8"
        options.quiet()
        options.addBooleanOption("Xdoclint:all,-reference", true)
        options.addMultilineStringsOption("tag").value =
            listOf("apiNote:a:API Note:", "implSpec:a:Implementation Requirements:", "implNote:a:Implementation Note:")
    }

    publishing {
        publications {
            create<MavenPublication>(name) {
                from(components["java"])

                pom {
                    name = project.name
                }
            }
        }
    }

    tasks.register("notifySonatypeRepository") {
        if (isSnapshotVersion) return@register

        val connection =
            (URI("https://ossrh-staging-api.central.sonatype.com/manual/upload/defaultRepository/com.sogeor.framework").toURL()
                .openConnection() as HttpURLConnection)
        connection.requestMethod = "POST"
        connection.setRequestProperty(
            "Authorization", "Basic ${
                Base64.getEncoder().encodeToString("${sonatypeUsername}:${sonatypePassword}".toByteArray())
            }"
        )

        connection.connect()
        println("Response (${connection.responseCode}): ${connection.responseMessage}")
    }

    tasks.register("publishAndNotify") {
        dependsOn("publish")
        dependsOn("notifySonatypeRepository")
        tasks.findByName("notifySonatypeRepository")?.mustRunAfter("publish")
    }
}
