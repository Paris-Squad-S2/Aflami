plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.paris_2.aflami.bottomNavBar.bottomNavBarUI"
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
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.bundles.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.bundles.navigation)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)

    implementation(projects.designsystem)
    implementation(projects.feature.home.homeApi)
    implementation(projects.feature.lists.listsApi)
    implementation(projects.feature.categories.categoriesApi)
    implementation(projects.feature.guessGame.guessGameApi)
    implementation(projects.feature.profile.profileApi)
    implementation(projects.feature.bottomNavBar.bottomNavBarApi)
    implementation(projects.domain.user)
}