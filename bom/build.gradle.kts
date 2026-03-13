description = "Представляет собой BOM модульного многофункционального фреймворка."

dependencies {
    constraints {
        rootProject.subprojects.forEach {
            if (it != project) api(it)
        }
    }
}

publishing {
    publications {
        withType<MavenPublication>() {
            pom {
                name = "Sogeor Framework BOM (Bill of Materials)"
                inceptionYear = "2026"
            }
        }
    }
}
