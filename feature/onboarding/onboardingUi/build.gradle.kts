plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = Configurations.NAME_SPACE_FEATURE_ONBOARDING_UI
    compileSdk = Configurations.COMPILE_SDK

    defaultConfig {
        minSdk = Configurations.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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

    implementation(libs.bundles.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(projects.feature.onboarding.onboardingApi)
    implementation(libs.bundles.pager)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)

    implementation(projects.feature.home.homeApi)
    implementation(projects.feature.home.homeUi)
    implementation(projects.feature.authentication.authenticationApi)
    implementation(projects.designsystem)
    implementation(projects.domain.user)
}