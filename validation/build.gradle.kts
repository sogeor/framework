description = "Предоставляет инструменты для валидации объектов и значений."

dependencies {
    api(project(":throwable"))

    implementation(project(":annotation"))
}

publishing {
    publications {
        withType<MavenPublication>() {
            pom {
                name = "Sogeor Framework Validation Module"
                inceptionYear = "2025"
            }
        }
    }
}
