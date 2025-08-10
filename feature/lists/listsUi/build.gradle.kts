plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.feature.lists.listsUi"
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

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)

    implementation(libs.bundles.navigation)

    implementation(libs.bundles.paging)

    implementation(libs.bundles.datetime)

    testImplementation(libs.bundles.test)


    implementation(projects.designsystem)
    implementation(projects.domain.lists)
    implementation(projects.domain.user)
    implementation(projects.feature.lists.listsApi)
    implementation(projects.safeimageviewer)
    implementation(projects.feature.authentication.authenticationApi)
    implementation(projects.feature.mediaDetails.mediaDetailsApi)

}