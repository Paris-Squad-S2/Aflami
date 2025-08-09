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
    namespace = "com.feature.search.searchUi"
    compileSdk = Configurations.COMPILE_SDK

    defaultConfig {
        minSdk = Configurations.MIN_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        buildConfig = true
    }


}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {


    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.material3) // TODO: Remove it

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

    implementation(project(Modules.DOMAIN_MEDIA))
    implementation(project(Modules.DESIGN_SYSTEM))
    implementation(project(Modules.SAFE_IMAGE_VIEWER))

    implementation(project(Modules.FEATURE_SEARCH_API))
    implementation(project(Modules.FEATURE_MEDIA_DETAILS_API))

    //kotlinx serialization
    implementation(libs.kotlinx.serialization.json)
}


kover {
    reports {
        total {
            verify {
                rule {
                    bound {
                        minValue = 60
                    }
                }
            }
        }
    }
}
