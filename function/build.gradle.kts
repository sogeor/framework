description = "Предоставляет функциональные инструменты."

dependencies {
    api(project(":validation"))

    implementation(project(":annotation"))

    testImplementation(platform("org.junit:junit-bom:${rootProject.ext["junit"]}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
