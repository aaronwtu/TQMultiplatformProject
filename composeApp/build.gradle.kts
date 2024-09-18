import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization")
    id("kotlin-parcelize")
//    id("com.google.devtools.ksp")
//    alias(libs.plugins.com.android.application)
//    alias(libs.plugins.org.jetbrains.kotlin.android)
//    alias(libs.plugins.androidx.baselineprofile)
//    alias(libs.plugins.compose)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")
    
    listOf(
        iosX64(),
        iosArm64(),
//        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
//        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.core.ktx)
            implementation(libs.coil.compose)

            implementation("io.insert-koin:koin-core:3.5.0")
            implementation("io.insert-koin:koin-android:3.5.0")
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.tracing.ktx)
            /// https://github.com/Kotlin/kotlinx-datetime?tab=readme-ov-file#using-in-your-projects
//            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
            implementation(libs.kotlinx.datetime)
            implementation(libs.core.ktx)
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

            /// Koin
            implementation("io.insert-koin:koin-core:3.5.0")
        }

        iosMain.dependencies {
//            implementation("io.ktor:ktor-client-ios:2.0.0")
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1-native-mt") {
//                version {
//                    strictly("1.5.1-native-mt")
//                }
//            }
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1-native-mt") {
//                version {
//                    strictly("1.5.1-native-mt")
//                }
//            }
        }
//        desktopMain.dependencies {
//            implementation(compose.desktop.currentOs)
//            implementation(libs.kotlinx.coroutines.swing)
//        }
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
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}
dependencies {
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.benchmark.baseline.profile.gradle.plugin)
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
