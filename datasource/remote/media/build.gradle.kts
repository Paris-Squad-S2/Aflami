plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = Configurations.NAME_SPACE_REMOTE_MEDIA
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
}

dependencies {
    implementation(libs.bundles.android)
    implementation(projects.repository.media)
    implementation(libs.bundles.retrofit)

    testImplementation(libs.bundles.test)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)
}