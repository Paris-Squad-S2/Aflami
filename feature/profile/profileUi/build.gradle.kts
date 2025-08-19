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
    }
}

dependencies {

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.bundles.compose)

    implementation(libs.bundles.webview)

    implementation(libs.bundles.datetime)

    implementation(libs.bundles.navigation)
    
    implementation(libs.bundles.serialization)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.bundles.test)

    implementation(projects.feature.profile.profileApi)
    implementation(projects.feature.authentication.authenticationApi)
    implementation(projects.designsystem)
    implementation(projects.safeimageviewer)
    implementation(projects.feature.mediaDetails.mediaDetailsApi)
    implementation(projects.domain.media)
    implementation(projects.domain.user)
    implementation(projects.domain.guessGame)
}
tasks.withType<Test> {
    useJUnitPlatform()
}

kover {
    reports {
        total {
            verify {
                rule {
                    bound {
                        minValue = 50
                    }
                }
            }
        }
    }
}
