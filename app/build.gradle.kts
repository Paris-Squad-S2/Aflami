import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.firebase.appdistribution)
    alias(libs.plugins.google.gms.google.services) apply true
    id("com.google.firebase.crashlytics")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.paris_2.aflami"
    compileSdk = Configurations.COMPILE_SDK

    signingConfigs {
        create("release") {
            val localProps = Properties().apply {
                val localFile = rootProject.file("local.properties")
                if (localFile.exists()) {
                    load(localFile.inputStream())
                }
            }

            val keystorePath = System.getenv("KEYSTORE_PATH")
                ?: localProps.getProperty("KEYSTORE_PATH")
                ?: throw GradleException("KEYSTORE_PATH is not set.")

            val keystorePassword = System.getenv("KEYSTORE_PASSWORD")
                ?: localProps.getProperty("KEYSTORE_PASSWORD")
                ?: throw GradleException("KEYSTORE_PASSWORD is not set.")

            val keyAliasValue = System.getenv("KEY_ALIAS")
                ?: localProps.getProperty("KEY_ALIAS")
                ?: throw GradleException("KEY_ALIAS is not set.")

            val keyPasswordValue = System.getenv("KEY_PASSWORD")
                ?: localProps.getProperty("KEY_PASSWORD")
                ?: throw GradleException("KEY_PASSWORD is not set.")

            storeFile = file(keystorePath)
            storePassword = keystorePassword
            keyAlias = keyAliasValue
            keyPassword = keyPasswordValue
        }
    }

    defaultConfig {
        applicationId = "com.paris_2.aflami"
        minSdk = Configurations.MIN_SDK_26
        targetSdk = Configurations.TARGET_SDK
        versionCode = Configurations.VERSION_CODE
        versionName = Properties().apply {
            load(file("release-info.txt").inputStream())
        }.getProperty("versionName")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true
    }



    buildTypes {
        getByName("release") {
            ndk {
                abiFilters.clear()
                abiFilters += listOf("armeabi-v7a", "arm64-v8a")
            }
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            isCrunchPngs = true

        }
        create("minified") {
            initWith(buildTypes.getByName("release"))
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            matchingFallbacks.add("release")
        }
        getByName("debug") {
            enableUnitTestCoverage = true
            isMinifyEnabled = false
            isShrinkResources = false
            ndk {
                abiFilters.clear()
                abiFilters += listOf("x86", "x86_64", "armeabi-v7a", "arm64-v8a")
            }
        }
    }
    compileOptions {
        sourceCompatibility = Configurations.JAVA_VERSION
        targetCompatibility = Configurations.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = Configurations.JVM_TARGET
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(project(Modules.DOMAIN_USER))
    implementation(project(Modules.FIREBASE))
    implementation(project(Modules.SAFE_IMAGE_VIEWER))
    implementation(project(Modules.FEATURE_SEARCH_UI))

    // JUnit 5
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.mockk)
    testImplementation(libs.junit.platform.launcher)

    //Koin
    implementation(libs.koin.workmanager)
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.android)

    //work manager for kotlin
    implementation(libs.work.runtime.ktx)

    implementation(libs.androidx.startup.runtime)

    implementation(project(Modules.FEATURE_SEARCH_API))
    implementation(project(Modules.FEATURE_MEDIA_DETAILS_API))
    implementation(project(Modules.FEATURE_MEDIA_DETAILS_UI))
    implementation(project(Modules.REPOSITORY_SEARCH))
    implementation(project(Modules.DATASOURCE_LOCAL_SEARCH))
    implementation(project(Modules.DATASOURCE_REMOTE_SEARCH))
    implementation(project(Modules.DATASOURCE_REMOTE_MEDIA_DETAILS))
    implementation(project(Modules.DOMAIN_SEARCH))
    implementation(project(Modules.DESIGN_SYSTEM))
    implementation(libs.androidx.room.runtime)
    ksp(libs.room.compiler)

    implementation(project(Modules.APP_NAVIGATION))

}

kover {
    reports {
        total {
            verify {
                rule {
                    bound {
                        minValue = 0
                    }
                }
            }
        }
    }
}