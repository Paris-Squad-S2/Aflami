plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies{
    testImplementation(libs.bundles.test)
    testImplementation(kotlin("test"))
    testImplementation(libs.mockk)
    testImplementation(libs.truth)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.jupiter.junit.jupiter)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)
}
tasks.test {
    useJUnitPlatform()
}
