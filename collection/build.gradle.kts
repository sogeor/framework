description = "Предоставляет фундаментальные коллекции."

dependencies {
    api(project(":common"))

    implementation(project(":annotation"))

    testImplementation(platform("org.junit:junit-bom:${rootProject.ext["junit"]}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
