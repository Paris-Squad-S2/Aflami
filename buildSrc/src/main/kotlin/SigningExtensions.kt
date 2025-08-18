import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.FileInputStream
import java.util.Properties

data class SigningConfig(
    val keystorePath: String,
    val keystorePassword: String,
    val keyAlias: String,
    val keyPassword: String
)

fun Project.getSigningConfig(): SigningConfig {
    val localProps = Properties().apply {
        val localFile = rootProject.file("local.properties")
        if (localFile.exists()) {
            load(FileInputStream(localFile))
        }
    }

    val keystorePath = System.getenv("KEYSTORE_PATH")
        ?: localProps.getProperty("KEYSTORE_PATH")
        ?: throw GradleException("KEYSTORE_PATH is not set.")

    val keystorePassword = System.getenv("KEYSTORE_PASSWORD")
        ?: localProps.getProperty("KEYSTORE_PASSWORD")
        ?: throw GradleException("KEYSTORE_PASSWORD is not set.")

    val keyAlias = System.getenv("KEY_ALIAS")
        ?: localProps.getProperty("KEY_ALIAS")
        ?: throw GradleException("KEY_ALIAS is not set.")

    val keyPassword = System.getenv("KEY_PASSWORD")
        ?: localProps.getProperty("KEY_PASSWORD")
        ?: throw GradleException("KEY_PASSWORD is not set.")

    return SigningConfig(keystorePath, keystorePassword, keyAlias, keyPassword)
}
