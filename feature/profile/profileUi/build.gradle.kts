plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.feature.profile.profileUi"
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
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.bundles.compose)
    implementation(libs.androidx.material3) // TODO: remove it

    implementation(libs.bundles.webview)

    implementation(libs.bundles.datetime)

    implementation(libs.bundles.navigation)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.bundles.test)

    implementation(project(Modules.FEATURE_PROFILE_API))
    implementation(project(Modules.FEATURE_AUTHENTICATION_API))
    implementation(project(Modules.DESIGN_SYSTEM))
    implementation(project(Modules.SAFE_IMAGE_VIEWER))
    implementation(project(Modules.FEATURE_MEDIA_DETAILS_API))
    implementation(project(Modules.DOMAIN_MEDIA))
    implementation(project(Modules.DOMAIN_USER))
}
