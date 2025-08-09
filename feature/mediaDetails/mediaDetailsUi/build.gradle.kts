plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.feature.mediaDetails.mediaDetailsUi"
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

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)

    implementation(libs.bundles.coil)
    implementation(libs.bundles.datetime)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.serialization)
    implementation(libs.bundles.paging)

    testImplementation(libs.bundles.test)

    implementation(project(Modules.DOMAIN_USER))
    implementation(project(Modules.DOMAIN_MEDIA))
    implementation(project(Modules.REPOSITORY_TV_SHOW))
    implementation(project(Modules.REPOSITORY_MOVIE))
    implementation(project(Modules.DOMAIN_LISTS))
    implementation(project(Modules.DESIGN_SYSTEM))
    implementation(project(Modules.FEATURE_MEDIA_DETAILS_API))
    implementation(project(Modules.FEATURE_AUTHENTICATION_API))
    implementation(project(Modules.SAFE_IMAGE_VIEWER))

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
