plugins {
    id("java")
}

group = rootProject.group
version = rootProject.version
description =
    "Представляет собой модуль для валидации аргументов методов и конструкторов, истинности и ложности условий, значений полей, анонимных значений, а также значений переменных."

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":throwable"))

    implementation("org.jetbrains:annotations:24.1.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    withJavadocJar()
    withSourcesJar()
}

tasks.javadoc {
    options.encoding = "UTF-8"
    val options = options as CoreJavadocOptions
    options.addStringOption("Xdoclint", "all")
    options.addStringOption("tag", "apiNote:a:\"API Note:\"")
    options.addStringOption("tag", "implSpec:a:\"Implementation Requirements:\"")
    options.addStringOption("tag", "implNote:a:\"Implementation Note:\"")
}

tasks.compileJava {
    options.encoding = "UTF-8"
}
