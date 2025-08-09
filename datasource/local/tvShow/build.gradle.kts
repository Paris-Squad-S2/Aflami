plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}
android {
    namespace = "com.datasource.local.tvshow"
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

    implementation(project(Modules.REPOSITORY_TV_SHOW))
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
