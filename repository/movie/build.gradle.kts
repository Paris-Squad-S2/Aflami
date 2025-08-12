plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = Configurations.NAME_SPACE_REPOSITORY_MOVIE
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

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
    implementation(libs.androidx.room.runtime)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.bundles.datetime)

    //kotlinx serialization
    implementation(libs.kotlinx.serialization.json)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    //work manager for kotlin
    implementation(libs.work.runtime.ktx)

    // test
    testImplementation(kotlin("test"))
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.truth)

    implementation(projects.domain.media)
    implementation(projects.repository.user)

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