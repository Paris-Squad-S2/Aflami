plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = Configurations.NAME_SPACE_FEATURE_CATEGORIES_UI
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
    implementation(projects.feature.categories.categoriesApi)
    implementation(projects.designsystem)

}