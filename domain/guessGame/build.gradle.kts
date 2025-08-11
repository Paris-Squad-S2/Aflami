plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = Configurations.JAVA_VERSION
    targetCompatibility = Configurations.JAVA_VERSION
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies{
    implementation(libs.kotlinx.datetime)
}
