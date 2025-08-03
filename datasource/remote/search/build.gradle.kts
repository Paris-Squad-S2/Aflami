plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.repository.search"
    compileSdk = Configurations.COMPILE_SDK

    defaultConfig {
        minSdk = Configurations.MIN_SDK_24

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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(project(Modules.REPOSITORY_MEDIA))

    api(libs.ktor.client.android)
    api(libs.ktor.client.core)
    api(libs.ktor.client.content.negotiation)
    api(libs.ktor.client.logging)
    api(libs.ktor.serialization.kotlinx.json)
    api(libs.ktor.client.auth)

    // Junit 5
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)

    // test
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(kotlin("test"))

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)

    //Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

}

val coverageMinValue: Int = (findProperty("coverageMinValue") as String).toInt()

kover {
    reports {
        total {
            verify {
                rule {
                    bound {
                        minValue = 0
                    }
                }
            }
        }
    }
}