description = "Предоставляет фундаментальные программные сбои и неисправности."

dependencies {
    implementation(project(":annotation"))
}

publishing {
    publications {
        withType<MavenPublication>() {
            pom {
                name = "Sogeor Framework Throwable Module"
                inceptionYear = "2025"
            }
        }
    }
}
