description = "Предоставляет фундаментальные инструменты."

dependencies {
    api(project(":function"))

    implementation(project(":annotation"))

    testImplementation(platform("org.junit:junit-bom:${rootProject.ext["junit"]}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
