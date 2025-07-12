import org.gradle.api.Project
import java.io.File
import java.util.Properties

fun Project.getApiToken(): String {
    val localProperties = Properties()
    val localPropertiesFile = File(rootProject.rootDir, "local.properties")

    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
        val tokenFromProperties = localProperties.getProperty("API_TOKEN")
        if (!tokenFromProperties.isNullOrBlank()) {
            return tokenFromProperties
        }
    }

    val tokenFromEnv = System.getenv("API_TOKEN")
    if (!tokenFromEnv.isNullOrBlank()) {
        return tokenFromEnv
    }

    return ""
}
