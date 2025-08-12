plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = Configurations.NAME_SPACE_FEATURE_MEDIA_DETAILS_UI
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
tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}


dependencies {
    implementation(libs.bundles.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)

    implementation(libs.bundles.coil)
    implementation(libs.bundles.datetime)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.serialization)
    implementation(libs.bundles.paging)

    testImplementation(libs.bundles.test)

    implementation(projects.domain.user)
    implementation(projects.domain.media)
    implementation(projects.repository.tvShows)
    implementation(projects.repository.movie)
    implementation(projects.domain.lists)
    implementation(projects.designsystem)
    implementation(projects.feature.authentication.authenticationApi)
    implementation(projects.feature.mediaDetails.mediaDetailsApi)
    implementation(projects.safeimageviewer)

    //Youtube Video Player
    implementation(libs.android.youtube.player)


}

kover {
    reports {
        total {
            verify {
                rule {
                    bound {
                        minValue =
                            0 //TODO set a minimum value for coverage and add tests to achieve it
                    }
                }
            }
        }
    }
}
