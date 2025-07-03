pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Aflami"
include(":app")
include(":designSystem")
include(":domain")
include(":domain:user")
include(":domain:guessGame")
include(":domain:media")
include(":domain:search")
include(":domain:lists")
include(":domain:home")
include(":repository")
include(":repository:movie")
include(":repository:tv_shows")
include(":repository:user")
include(":repository:lists")
include(":repository:categories")
include(":repository:guessGame")
include(":datasource")
include(":datasource:remote")
include(":datasource:local")
include(":datasource:remote:user")
include(":datasource:local:user")
include(":datasource:remote:movie")
include(":datasource:local:movie")
include(":datasource:remote:tv_show")
include(":datasource:local:tv_show")
include(":datasource:remote:lists")
include(":datasource:local:lists")
include(":datasource:local:guessGame")
include(":datasource:remote:categories")
include(":datasource:local:categories")
