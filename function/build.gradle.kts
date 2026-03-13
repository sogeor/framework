description = "Предоставляет функциональные инструменты."

dependencies {
    api(project(":validation"))

    implementation(project(":annotation"))
}

publishing {
    publications {
        withType<MavenPublication>() {
            pom {
                name = "Sogeor Framework Function Module"
                inceptionYear = "2025"
            }
        }
    }
}
