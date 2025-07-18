plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.feature.mediaDetails.mediaDetailsUi"
    compileSdk = Configurations.COMPILE_SDK

    defaultConfig {
        minSdk = Configurations.MIN_SDK_26

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

    implementation(project(Modules.DOMAIN_MEDIA_DETAILS))
    implementation(project(Modules.REPOSITORY_TV_SHOW))
    implementation(project(Modules.REPOSITORY_MOVIE))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(project(Modules.APP_NAVIGATION))
    implementation(libs.androidx.material3)

    //Koin
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.android)


    implementation(project(Modules.FEATURE_MEDIA_DETAILS_API))
    //coil
    implementation(libs.coil.compose)

    //Kotlinx DateTime
    implementation(libs.kotlinx.datetime)

    //Navigation
    implementation(libs.navigation.compose)

    //kotlinx serialization
    implementation(libs.kotlinx.serialization.json)

    // paging 3
    implementation(libs.androidx.paging.runtime)
    implementation (libs.androidx.paging.compose)

    implementation(project(Modules.DESIGN_SYSTEM))

    implementation(project(Modules.DOMAIN_MEDIA_DETAILS))
    implementation(project(Modules.FEATURE_MEDIA_DETAILS_API))
    implementation(project(Modules.SAFE_IMAGE_VIEWER))

}