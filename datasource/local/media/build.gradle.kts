plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = Configurations.NAME_SPACE_LOCAL_MEDIA
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
tasks.withType<Test> {
    useJUnitPlatform()
}


dependencies {
    //Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.bundles.datetime)

    implementation(libs.bundles.serialization)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)

    implementation(libs.bundles.workManager)

    testImplementation(libs.bundles.test)
    implementation(projects.repository.media)
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