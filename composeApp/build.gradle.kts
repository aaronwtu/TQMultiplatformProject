import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    /// Gradle 插件
//    kotlin("multiplatform")
//    kotlin("native.cocoapods")

    kotlin("multiplatform")
    kotlin("native.cocoapods")

    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1"

    //
    kotlin("plugin.serialization")


//    id("com.google.devtools.ksp")
//    alias(libs.plugins.com.android.application)
//    alias(libs.plugins.org.jetbrains.kotlin.android)
//    alias(libs.plugins.androidx.baselineprofile)
//    alias(libs.plugins.compose)
}

kotlin {

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    cocoapods {
        summary = "Kotlin sample project with CocoaPods dependencies"
        homepage = "https://github.com/Kotlin/kotlin-with-cocoapods-sample"
        version = "1.15.0"
        ios.deploymentTarget = "15"

        /// Swift 库
        pod("Alamofire") {
           source = git("https://github.com/Alamofire/Alamofire.git") {
               tag = "5.10.0"
               extraOpts += listOf("-compiler-option", "-fmodules")
           }
        }

        /// OC 库
        pod("AFNetworking") {
            version = "~> 4.0.0"
        }
        pod("SDWebImage") {
            source = git("https://github.com/SDWebImage/SDWebImage.git") {
                tag = "5.9.2"
            }
        }

        /// Example of usage local Cocoapods dependency
//        pod("pod_dependency") {
//            version = "1.0"
//            source = path(project.file("../pod_dependency"))
//        }

        /// Example of usage local Pod declared as Subspec
//        pod("subspec_dependency/Core") {
//            version = "1.0"
//            source = path(project.file("../subspec_dependency"))
//        }
    }

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")


    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.core.ktx)
            implementation(libs.coil.compose)

            implementation("io.insert-koin:koin-core:3.5.0")
            implementation("io.insert-koin:koin-android:3.5.0")
            implementation("androidx.camera:camera-camera2:1.3.1")
            implementation("androidx.camera:camera-lifecycle:1.3.1")
            implementation("androidx.camera:camera-view:1.3.1")
            implementation("androidx.lifecycle:lifecycle-viewmodel-compose:{latest_version}")
            implementation("com.google.accompanist:accompanist-permissions:0.29.2-rc")
            implementation("com.google.android.gms:play-services-maps:18.2.0")
            implementation("com.google.android.gms:play-services-location:21.1.0")
            implementation("com.google.maps.android:maps-compose:2.11.2")
            implementation(libs.androidx.tracing.ktx)
            /// life cycle, 低版本会导致一个crash
            /// java.lang.IllegalStateException: CompositionLocal LocalLifecycleOwner not present
            implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
            /// TODO: 这个库为啥用不了？
            /// implementation("org.jetbrains.kotlinx:kotlinx-parcelize-runtime:2.0.21")
        }

        commonMain.dependencies {
            // redux
            implementation(libs.redux.kotlin)
            implementation(libs.redux.kotlin.thunk)
            implementation(libs.redux.kotlin.threadsafe)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            /// https://github.com/Kotlin/kotlinx-datetime?tab=readme-ov-file#using-in-your-projects
//            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
            implementation(libs.kotlinx.datetime)

            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
            /// Koin
            implementation("io.insert-koin:koin-core:3.5.0")
            /// Log
//            implementation("io.github.microutils:kotlin-logging:2.0.11")
//            implementation("io.github.oshai:kotlin-logging-jvm:5.1.4")
            // https://mvnrepository.com/artifact/io.ktor/ktor-client-logging
//            implementation("io.ktor:ktor-client-logging:jar:2.3.12")


        }
        iosMain.dependencies {
        }
    }
}

android {
    namespace = "org.aaronwtlu.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.aaronwtlu.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}
dependencies {
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.benchmark.baseline.profile.gradle.plugin)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.leanback)
}

compose.desktop {
    application {
        mainClass = "org.aaronwtlu.project.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.aaronwtlu.project"
            packageVersion = "1.0.0"
        }
    }
}
