import org.gradle.api.Project
import java.io.File
import java.util.Properties

fun Project.getApiToken(): String {
    val localPropertiesFile = File(rootProject.rootDir, "local.properties")
    return if (localPropertiesFile.exists()) {
        Properties().apply { load(localPropertiesFile.inputStream()) }
            .getProperty("API_TOKEN") ?: ""
    } else ""
}
