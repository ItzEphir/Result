plugins {
    kotlin("jvm") version "2.0.20"
}

group = "com.github.ItzEphir"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin{
    explicitApi()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}