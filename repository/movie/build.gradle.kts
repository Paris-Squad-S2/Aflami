plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies{
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.common.jvm)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter)
    implementation(libs.kotlinx.datetime)

    //kotlinx serialization
    implementation(libs.kotlinx.serialization.json)


    implementation(project(Modules.DOMAIN_MEDIA_DETAILS))
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
