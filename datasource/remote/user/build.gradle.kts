plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = Configurations.NAME_SPACE_REMOTE_USER
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
    implementation(libs.bundles.serialization)

    testImplementation(libs.bundles.test)
    //test
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(kotlin("test"))

    implementation(libs.bundles.retrofit)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)

    implementation(projects.repository.user)
}
tasks.withType<Test> {
    useJUnitPlatform()
}
val coverageMinValue: Int = (findProperty("coverageMinValue") as String).toInt()

kover {
    reports {
        total {
            verify {
                rule {
                    bound {
                        minValue = coverageMinValue
                    }
                }
            }
        }
    }
}