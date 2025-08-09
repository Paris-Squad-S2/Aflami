plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.feature.onboarding.onboardingUi"
    compileSdk = Configurations.COMPILE_SDK

    defaultConfig {
        minSdk = Configurations.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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

    implementation(project(Modules.FEATURE_ONBOARDING_API))
    implementation(libs.bundles.pager)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)

    implementation(project(Modules.FEATURE_HOME_API))
    implementation(project(Modules.FEATURE_HOME_UI))
    implementation(project(Modules.FEATURE_AUTHENTICATION_API))
    implementation(project(Modules.DESIGN_SYSTEM))
    implementation(project(Modules.DOMAIN_USER))
}