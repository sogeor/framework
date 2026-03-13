description = "Предоставляет фундаментальные коллекции."

dependencies {
    api(project(":common"))
    implementation(project(":annotation"))
}

publishing {
    publications {
        withType<MavenPublication>() {
            pom {
                name = "Sogeor Framework Collection Module"
                inceptionYear = "2025"
            }
        }
    }
}
