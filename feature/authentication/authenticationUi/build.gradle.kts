plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.feature.authentication.authenticationUi"
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

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}


dependencies {

    implementation(libs.bundles.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3) //TODO : Remove it
    implementation(libs.bundles.navigation)
    testImplementation(libs.bundles.test)
    implementation(libs.bundles.webview)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)


    implementation(project(Modules.FEATURE_AUTHENTICATION_API))
    implementation(project(Modules.DESIGN_SYSTEM))
    implementation(project(Modules.FEATURE_BOTTOM_NAV_BAR_API))
    implementation(project(Modules.DOMAIN_USER))
}