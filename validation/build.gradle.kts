description = "Предоставляет инструменты для валидации объектов и значений."

dependencies {
    api(project(":throwable"))

    implementation(project(":annotation"))

    testImplementation(platform("org.junit:junit-bom:${rootProject.ext["junit"]}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
