plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.designSystem.safeimageviewer"
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
        mlModelBinding = true
        compose = true
        // Disable unused build features
        buildConfig = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Configurations.KOTLIN_COMPILER
    }
}

dependencies {
    // Compose dependencies
    implementation(libs.bundles.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.coil)

    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // TensorFlow Lite - core only (minimal dependencies)
    implementation(libs.bundles.tensorflow)
}