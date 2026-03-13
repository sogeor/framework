import org.jreleaser.model.Active
import org.jreleaser.model.Http

plugins {
    java
    `maven-publish`
    id("org.jreleaser").version("1.23.0")
}

description = "Представляет собой модульный многофункциональный фреймворк."

tasks.wrapper {
    gradleVersion = "9.4.0"
}

allprojects {
    group = "com.sogeor.framework"
    version = "1.0.0-SNAPSHOT"
}

subprojects {
    val notBom = name != "bom"
    if (notBom) apply<JavaLibraryPlugin>() else apply<JavaPlatformPlugin>()
    apply<MavenPublishPlugin>()

    repositories {
        mavenLocal()
        mavenCentral()
    }

    val testable = notBom && name !in arrayOf("annotation", "throwable")
    if (testable) dependencies {
        testImplementation(platform("org.junit:junit-bom:${property("junit")}"))
        testImplementation("org.junit.platform:junit-platform-launcher")
        testImplementation("org.junit.jupiter:junit-jupiter-engine")
    }

    publishing {
        publications {
            create<MavenPublication>(name) {
                from(components[if (notBom) "java" else "javaPlatform"])

                pom {
                    description = project.description
                    url = "https://github.com/sogeor/framework"

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

    if (notBom) configure<JavaPluginExtension> {
        withJavadocJar()
        withSourcesJar()

        modularity.inferModulePath = true

        toolchain {
            languageVersion = JavaLanguageVersion.of(25)
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    if (testable) tasks.withType<Test> {
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
}

jreleaser {
    project {
        name = "Sogeor Framework"
        license = "Apache License, Version 2.0"
        inceptionYear = "2025"
        vendor = "Sogeor"
        authors = listOf("George Sopin")
        tags = listOf("java", "framework")
        maintainers = listOf("Bloogefest")

        links {
            homepage = "https://github.com/sogeor/framework"
            documentation = "https://github.com/sogeor/framework/wiki"
            license = "https://www.apache.org/licenses/LICENSE-2.0"
            bugTracker = "https://github.com/sogeor/framework/issues"
            vcsBrowser = "https://github.com/sogeor/framework"
        }
    }

    val isSnapshot = "-SNAPSHOT" in version.toString()

    if (!isSnapshot) release {
        github {
            host = "github.com"
            repoOwner = "sogeor"
            sign = true
            immutableRelease = true
        }
    }

    deploy {
        maven {
            mavenCentral {
                register(project.name.toString()) {
                    active = Active.ALWAYS
                    authorization = Http.Authorization.BEARER
                    stagingRepository("build/staging")
                    applyMavenCentralRules = true
                    snapshotSupported = isSnapshot
                }
            }
        }
    }

    signing {
        active = Active.ALWAYS

        pgp {
            active = Active.ALWAYS
        }
    }
}
