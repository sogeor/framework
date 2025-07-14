# Sogeor Framework

[![RELEASE](https://img.shields.io/github/v/release/sogeor/framework?style=for-the-badge)](https://github.com/sogeor/framework/releases/latest)
[![CI/CD](https://img.shields.io/github/actions/workflow/status/sogeor/framework/master.yml?style=for-the-badge&label=CI%2FCD)](https://github.com/sogeor/framework/actions/workflows/master.yml)

## Введение

Представляет собой модульный многофункциональный фреймворк.

## Подключение к проекту

### Снимок

```kotlin
repositories {
    maven("https://central.sonatype.com/repository/maven-snapshots/")
}

dependencies {
    implementation(platform("com.sogeor.framework:bom:1.0.0-SNAPSHOT"))
    implementation("com.sogeor.framework:annotation")
    implementation("com.sogeor.framework:collection")
    implementation("com.sogeor.framework:common")
    implementation("com.sogeor.framework:function")
    implementation("com.sogeor.framework:throwable")
    implementation("com.sogeor.framework:validation")
}
```

## Модули

### `com.sogeor.framework.annotation`

Предоставляет фундаментальные аннотации.

### `com.sogeor.framework.collection`

Предоставляет фундаментальные коллекции.

### `com.sogeor.framework.common`

Предоставляет фундаментальные инструменты.

### `com.sogeor.framework.function`

Предоставляет функциональные инструменты.

### `com.sogeor.framework.throwable`

Предоставляет фундаментальные программные сбои и неисправности.

### `com.sogeor.framework.validation`

Предоставляет инструменты для валидации объектов и значений.
