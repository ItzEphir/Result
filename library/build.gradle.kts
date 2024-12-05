import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.swiftexport.ExperimentalSwiftExportDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "io.github.ItzEphir"
version = "1.0.0"

kotlin {
    withSourcesJar(publish = false)
    explicitApi()

    jvm {
        withSourcesJar(publish = true)
    }
    androidTarget {
        publishLibraryVariants("debug")
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
    linuxArm64()
    js {
        browser()
        nodejs()
    }
    macosX64()
    macosArm64()
    watchosArm32()
    watchosArm64()
    watchosDeviceArm64()
    watchosSimulatorArm64()
    watchosX64()
    tvosX64()
    tvosArm64()
    tvosSimulatorArm64()

    @OptIn(ExperimentalSwiftExportDsl::class)
    swiftExport {
        moduleName = "Result"

        flattenPackage = "io.github.ItzEphir.result"
    }

    sourceSets {
        commonMain.dependencies {

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "io.github.itzephir.result"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "result", version.toString())

    pom {
        name = "Result"
        description = "Custom Result class with useful functionality"
        inceptionYear = "2024"
        url = "https://github.com/ItzEphir/Result"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "itzephir"
                name = "Dmitry Dvoryannikov"
                url = "itephir@gmail.com"
            }
        }
        scm {
            connection = "scm:git:git://github.com/ItzEphir/Result.git"
            developerConnection = "scm:git:ssh://github.com/ItzEphir"
            url = "https://github.com/ItzEphir/Result"
        }
    }
}