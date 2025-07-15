    plugins {
        alias(libs.plugins.android.library)
        alias(libs.plugins.kotlin.android)
        alias(libs.plugins.kotlin.compose)
    }
    
    android {
        namespace = "com.paris_2.aflami.designsystem"
        compileSdk = Configurations.COMPILE_SDK
    
        defaultConfig {
            minSdk = Configurations.MIN_SDK_26
    
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
            vectorDrawables.useSupportLibrary = true
        }
    
        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            debug {
                isMinifyEnabled = false
            }
        }
    
        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        kotlin {
            compilerOptions {
                jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
            }
        }
        buildFeatures {
            compose = true
            // Disable unused build features
            buildConfig = false
            aidl = false
            renderScript = false
            resValues = false
            shaders = false
        }
    
    }

    kotlin {
        sourceSets.all {
            languageSettings {
                compilerOptions {
                    freeCompilerArgs.add("-Xannotation-default-target=param-property")
                }
            }
        }
    }

    dependencies {
    
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
    
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        debugImplementation(libs.androidx.ui.tooling)
    
        implementation(libs.accompanist.pager)
        coreLibraryDesugaring(libs.desugar.jdk.libs)
        implementation(project(Modules.SAFE_IMAGE_VIEWER))
    }