plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

}

android {
    namespace = "com.parise_2.logger"
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
    testImplementation(libs.bundles.test)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
}