import org.jetbrains.kotlin.gradle.dsl.JvmTarget.Companion.fromTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.firebase.appdistribution)
    alias(libs.plugins.google.gms.google.services) apply true
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.paris_2.aflami"
    compileSdk = Configurations.COMPILE_SDK

    signingConfigs {
        create("release") {
            val signingConfig = project.getSigningConfig()
            storeFile = file(signingConfig.keystorePath)
            storePassword = signingConfig.keystorePassword
            keyAlias = signingConfig.keyAlias
            keyPassword = signingConfig.keyPassword
        }
    }

    defaultConfig {
        applicationId = "com.paris_2.aflami"
        minSdk = Configurations.MIN_SDK
        targetSdk = Configurations.TARGET_SDK
        versionCode = Configurations.VERSION_CODE
        versionName = Properties().apply {
            load(file("release-info.txt").inputStream())
        }.getProperty("versionName")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        buildConfigField("String", "API_TOKEN", "\"${getApiToken()}\"")

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
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.bundles.compose)
    implementation(libs.bundles.datastore)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.workManager)
    implementation(libs.bundles.room)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.serialization)
    implementation(libs.androidx.core.splashscreen)

    ksp(libs.hilt.android.compiler)
    ksp(libs.room.compiler)

    implementation(projects.logger)
    implementation(projects.safeimageviewer)

    implementation(projects.feature.bottomNavBar.bottomNavBarApi)
    implementation(projects.feature.search.searchApi)
    implementation(projects.feature.home.homeApi)
    implementation(projects.feature.lists.listsApi)
    implementation(projects.feature.profile.profileApi)
    implementation(projects.feature.categories.categoriesApi)
    implementation(projects.feature.guessGame.guessGameApi)
    implementation(projects.feature.authentication.authenticationApi)
    implementation(projects.feature.mediaDetails.mediaDetailsApi)
    implementation(projects.feature.onboarding.onboardingApi)

    implementation(projects.feature.bottomNavBar.bottomNavBarUI)
    implementation(projects.feature.search.searchUi)
    implementation(projects.feature.home.homeUi)
    implementation(projects.feature.lists.listsUi)
    implementation(projects.feature.profile.profileUi)
    implementation(projects.feature.categories.categoriesUi)
    implementation(projects.feature.guessGame.guessGameUi)
    implementation(projects.feature.authentication.authenticationUi)
    implementation(projects.feature.mediaDetails.mediaDetailsUi)
    implementation(projects.feature.onboarding.onboardingUi)

    implementation(projects.repository.lists)
    implementation(projects.repository.user)
    implementation(projects.repository.media)
    implementation(projects.repository.guessGame)

    implementation(projects.datasource.remote.user)
    implementation(projects.datasource.remote.media)
    implementation(projects.datasource.remote.lists)

    implementation(projects.datasource.local.media)
    implementation(projects.datasource.local.user)
    implementation(projects.datasource.local.guessGame)

    implementation(projects.domain.media)
    implementation(projects.domain.lists)
    implementation(projects.domain.user)
    implementation(projects.domain.guessGame)
    implementation(projects.designsystem)

}

kotlin {
    compilerOptions { jvmTarget.set(fromTarget(Configurations.JVM_TARGET)) }
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