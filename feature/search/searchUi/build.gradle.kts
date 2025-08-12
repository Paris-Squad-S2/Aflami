plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = Configurations.NAME_SPACE_FEATURE_SEARCH_UI
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
        buildConfig = true
    }


}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {


    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    //Navigation
    implementation(libs.bundles.navigation)

    //Room
    implementation(libs.androidx.room.runtime) // TODO: use bundles
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)


    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)

    implementation(libs.bundles.serialization)

    implementation(libs.bundles.datetime)

    implementation(libs.bundles.coil)

    implementation(libs.bundles.paging)

    testImplementation(libs.bundles.test)

    implementation(projects.domain.media)
    implementation(projects.domain.user)
    implementation(projects.designsystem)
    implementation(projects.safeimageviewer)

    implementation(projects.feature.search.searchApi)
    implementation(projects.feature.mediaDetails.mediaDetailsApi)

    //kotlinx serialization
    implementation(libs.kotlinx.serialization.json)
}


kover {
    reports {
        total {
            verify {
                rule {
                    bound {
                        //todo:return coverageMinValue
                        minValue = 60
                    }
                }
            }
        }
    }
}
