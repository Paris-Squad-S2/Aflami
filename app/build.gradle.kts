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
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = false
            isCrunchPngs = true
        }
        create("minified") {
            initWith(getByName("debug"))
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            matchingFallbacks.add("debug")
        }
        getByName("debug") {
            enableUnitTestCoverage = true
            isMinifyEnabled = false
            isShrinkResources = false
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

    //Navigation
    implementation(libs.navigation.compose)

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

    implementation(project(Modules.FEATURE_HOME_API))
    implementation(project(Modules.FEATURE_LISTS_API))
    implementation(project(Modules.FEATURE_PROFILE_API))
    implementation(project(Modules.FEATURE_CATEGORIES_API))
    implementation(project(Modules.FEATURE_GUESS_GAME_API))

    implementation(project(Modules.FEATURE_HOME_UI))
    implementation(project(Modules.FEATURE_LISTS_UI))
    implementation(project(Modules.FEATURE_PROFILE_UI))
    implementation(project(Modules.FEATURE_CATEGORIES_UI))
    implementation(project(Modules.FEATURE_GUESS_GAME_UI))


    implementation(project(Modules.FEATURE_MEDIA_DETAILS_API))
    implementation(project(Modules.FEATURE_MEDIA_DETAILS_UI))
    implementation(project(Modules.REPOSITORY_SEARCH))
    implementation(project(Modules.REPOSITORY_TV_SHOW))
    implementation(project(Modules.REPOSITORY_MOVIE))
    implementation(project(Modules.DATASOURCE_LOCAL_SEARCH))
    implementation(project(Modules.DATASOURCE_REMOTE_SEARCH))
    implementation(project(Modules.DATASOURCE_REMOTE_TV_SHOW))
    implementation(project(Modules.DATASOURCE_REMOTE_MOVIE))
    implementation(project(Modules.DOMAIN_SEARCH))
    implementation(project(Modules.DOMAIN_MEDIA_DETAILS))
    implementation(project(Modules.DATASOURCE_LOCAL_MOVIE))
    implementation(project(Modules.DATASOURCE_LOCAL_TV_SHOW))
    implementation(project(Modules.DESIGN_SYSTEM))
    implementation(libs.androidx.room.runtime)
    ksp(libs.room.compiler)

    implementation(project(Modules.APP_NAVIGATION))


    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.logging)
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