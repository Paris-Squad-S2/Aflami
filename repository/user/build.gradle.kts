plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.paris.repository.user"
    compileSdk = Configurations.COMPILE_SDK

    defaultConfig {
        minSdk = Configurations.MIN_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)
    testImplementation(libs.bundles.test)

    implementation(projects.domain.user)
}

tasks.withType<Test> {
    useJUnitPlatform()
}