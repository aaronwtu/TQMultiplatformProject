plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false

    /// 跨平台插件
    kotlin("multiplatform").version("2.0.0").apply(false)
    kotlin("native.cocoapods").version("2.1.0-RC2").apply(false)
}

buildscript {
    repositories {
        mavenCentral()
    }
}
