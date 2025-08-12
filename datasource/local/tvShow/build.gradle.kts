plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}
android {
    namespace = Configurations.NAME_SPACE_LOCAL_TVSHOW
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
    sourceSets["main"].assets.srcDir("schemas")
}
tasks.withType<Test> {
    useJUnitPlatform()
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.common.jvm)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.android.compiler)

    implementation(libs.bundles.datetime)

    implementation(libs.bundles.serialization)

    implementation(libs.bundles.workManager)

    testImplementation(libs.bundles.test)

    implementation(projects.repository.tvShows)
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
