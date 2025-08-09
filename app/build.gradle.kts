import org.jetbrains.kotlin.gradle.dsl.JvmTarget.Companion.fromTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.firebase.appdistribution)
    alias(libs.plugins.google.gms.google.services) apply true
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.paris_2.aflami"
    compileSdk = Configurations.COMPILE_SDK

    signingConfigs {
        create("release") {
            val localProps = Properties().apply {
                val localFile = rootProject.file("local.properties")
                if (localFile.exists()) {
                    load(localFile.inputStream())
                }
            }

            val keystorePath = System.getenv("KEYSTORE_PATH")
                ?: localProps.getProperty("KEYSTORE_PATH")
                ?: throw GradleException("KEYSTORE_PATH is not set.")

            val keystorePassword = System.getenv("KEYSTORE_PASSWORD")
                ?: localProps.getProperty("KEYSTORE_PASSWORD")
                ?: throw GradleException("KEYSTORE_PASSWORD is not set.")

            val keyAliasValue = System.getenv("KEY_ALIAS")
                ?: localProps.getProperty("KEY_ALIAS")
                ?: throw GradleException("KEY_ALIAS is not set.")

            val keyPasswordValue = System.getenv("KEY_PASSWORD")
                ?: localProps.getProperty("KEY_PASSWORD")
                ?: throw GradleException("KEY_PASSWORD is not set.")

            storeFile = file(keystorePath)
            storePassword = keystorePassword
            keyAlias = keyAliasValue
            keyPassword = keyPasswordValue
        }
    }

    defaultConfig {
        applicationId = "com.paris_2.aflami"
        minSdk = Configurations.MIN_SDK
        targetSdk = Configurations.TARGET_SDK
        versionCode = Configurations.VERSION_CODE
        versionName = Properties().apply {
            load(file("release-info.txt").inputStream())
        }.getProperty("versionName")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true

        buildConfigField("String", "API_TOKEN", "\"${getApiToken()}\"")

    }



    buildTypes {
        getByName("release") {
            ndk {
                abiFilters.clear()
                abiFilters += listOf("armeabi-v7a", "arm64-v8a")
            }
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            isCrunchPngs = true

        }
        create("minified") {
            initWith(buildTypes.getByName("release"))
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            matchingFallbacks.add("release")
        }
        getByName("debug") {
            enableUnitTestCoverage = true
            isMinifyEnabled = false
            isShrinkResources = false
            ndk {
                abiFilters.clear()
                abiFilters += listOf("x86", "x86_64", "armeabi-v7a", "arm64-v8a")
            }
        }
    }
    compileOptions {
        sourceCompatibility = Configurations.JAVA_VERSION
        targetCompatibility = Configurations.JAVA_VERSION
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.bundles.compose)
    implementation(libs.bundles.datastore)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.workManager)
    implementation(libs.bundles.room)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.serialization)

    ksp(libs.hilt.android.compiler)
    ksp(libs.room.compiler)

    implementation(project(Modules.DESIGN_SYSTEM))
    implementation(project(Modules.LOGGER))
    implementation(project(Modules.SAFE_IMAGE_VIEWER))

    implementation(project(Modules.FEATURE_BOTTOM_NAV_BAR_API))
    implementation(project(Modules.FEATURE_BOTTOM_NAV_BAR_UI))
    implementation(project(Modules.FEATURE_SEARCH_API))
    implementation(project(Modules.FEATURE_HOME_API))
    implementation(project(Modules.FEATURE_LISTS_API))
    implementation(project(Modules.FEATURE_PROFILE_API))
    implementation(project(Modules.FEATURE_CATEGORIES_API))
    implementation(project(Modules.FEATURE_GUESS_GAME_API))
    implementation(project(Modules.FEATURE_AUTHENTICATION_API))
    implementation(project(Modules.FEATURE_MEDIA_DETAILS_API))
    implementation(project(Modules.FEATURE_ONBOARDING_API))

    implementation(project(Modules.FEATURE_HOME_UI))
    implementation(project(Modules.FEATURE_LISTS_UI))
    implementation(project(Modules.FEATURE_PROFILE_UI))
    implementation(project(Modules.FEATURE_CATEGORIES_UI))
    implementation(project(Modules.FEATURE_GUESS_GAME_UI))
    implementation(project(Modules.FEATURE_AUTHENTICATION_UI))
    implementation(project(Modules.FEATURE_MEDIA_DETAILS_UI))
    implementation(project(Modules.FEATURE_SEARCH_UI))
    implementation(project(Modules.FEATURE_ONBOARDING_UI))

    implementation(project(Modules.REPOSITORY_TV_SHOW))
    implementation(project(Modules.REPOSITORY_LISTS))
    implementation(project(Modules.REPOSITORY_MOVIE))
    implementation(project(Modules.REPOSITORY_USER))

    implementation(project(Modules.DATASOURCE_LOCAL_MEDIA))
    implementation(project(Modules.DATASOURCE_REMOTE_LISTS))
    implementation(project(Modules.DATASOURCE_REMOTE_TV_SHOW))
    implementation(project(Modules.DATASOURCE_REMOTE_MOVIE))
    implementation(project(Modules.DATASOURCE_REMOTE_USER))

    implementation(project(Modules.DATASOURCE_LOCAL_MOVIE))
    implementation(project(Modules.DATASOURCE_LOCAL_TV_SHOW))
    implementation(project(Modules.DATASOURCE_LOCAL_USER))

    implementation(project(Modules.DOMAIN_MEDIA))
    implementation(project(Modules.DOMAIN_LISTS))
    implementation(project(Modules.DATASOURCE_LOCAL_MOVIE))
    implementation(project(Modules.DATASOURCE_LOCAL_TV_SHOW))
    implementation(project(Modules.DESIGN_SYSTEM))
    implementation(project(Modules.REPOSITORY_MEDIA))
    implementation(project(Modules.DATASOURCE_REMOTE_MEDIA))
    implementation(project(Modules.DOMAIN_USER))

}

kotlin {
    compilerOptions { jvmTarget.set(fromTarget(Configurations.JVM_TARGET)) }
}

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