plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.datasource.local.movie"
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
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation(projects.repository.movie)

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.common.jvm)
    testImplementation(libs.junit.jupiter)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
    implementation(libs.androidx.room.ktx)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    //work manager for kotlin
    implementation(libs.work.runtime.ktx)

    // kotlin date time
    implementation(libs.kotlinx.datetime)

    //kotlinx serialization
    implementation(libs.kotlinx.serialization.json)

    // test
    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.mockk)
    implementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.kotlin.test.junit5)
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
