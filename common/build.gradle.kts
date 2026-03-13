description = "Предоставляет фундаментальные инструменты."

dependencies {
    api(project(":function"))

    implementation(project(":annotation"))
}

publishing {
    publications {
        withType<MavenPublication>() {
            pom {
                name = "Sogeor Framework Common Module"
                inceptionYear = "2025"
            }
        }
    }
}
