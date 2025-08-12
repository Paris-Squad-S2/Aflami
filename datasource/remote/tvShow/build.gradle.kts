plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = Configurations.NAME_SPACE_REMOTE_TVSHOW
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
}

dependencies {

    implementation(libs.bundles.android)
    implementation(libs.bundles.retrofit)

    implementation(projects.repository.movie)
    implementation(projects.repository.tvShows)

    testImplementation(libs.bundles.test)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)
}

val coverageMinValue: Int = (findProperty("coverageMinValue") as String).toInt()

kover {
    reports {
        total {
            verify {
                rule {
                    bound {
                        //todo:return coverageMinValue
                        minValue = 0
                    }
                }
            }
        }
    }
}