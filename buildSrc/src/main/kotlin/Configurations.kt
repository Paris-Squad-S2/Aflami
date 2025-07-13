import org.gradle.api.JavaVersion

object Configurations {
    const val COMPILE_SDK = 35
    const val MIN_SDK_24 = 24
    const val MIN_SDK_26 = 26
    const val TARGET_SDK = 35
    const val VERSION_CODE = 1
    const val VERSION_NAME = "0.1.1"
    const val JVM_TARGET = "11"
    const val KOTLIN_COMPILER = "1.5.4"
    @JvmField val JAVA_VERSION = JavaVersion.VERSION_11
}