enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Aflami"
include(":app")
include(":designsystem")
include(":logger")
include(":domain")
include(":domain:user")
include(":domain:guessGame")
include(":domain:media")
include(":domain:lists")
include(":repository")
include(":repository:lists")
include(":repository:guessGame")
include(":repository:media")
include(":repository:user")
include(":datasource")
include(":datasource:remote")
include(":datasource:remote:user")
include(":datasource:remote:media")
include(":datasource:remote:lists")
include(":datasource:local")
include(":datasource:local:guessGame")
include(":datasource:local:media")
include(":datasource:local:user")
include(":feature")
include(":feature:onboarding")
include(":feature:onboarding:onboardingApi")
include(":feature:authentication")
include(":feature:authentication:authenticationApi")
include(":feature:home")
include(":feature:home:homeApi")
include(":feature:onboarding:onboardingUi")
include(":feature:authentication:authenticationUi")
include(":feature:home:homeUi")
include(":feature:profile")
include(":feature:profile:profileApi")
include(":feature:profile:profileUi")
include(":feature:search")
include(":feature:search:searchApi")
include(":feature:search:searchUi")
include(":feature:mediaDetails")
include(":feature:mediaDetails:mediaDetailsApi")
include(":feature:mediaDetails:mediaDetailsUi")
include(":feature:guessGame")
include(":feature:guessGame:guessGameApi")
include(":feature:guessGame:guessGameUi")
include(":feature:categories")
include(":feature:categories:categoriesApi")
include(":feature:categories:categoriesUi")
include(":feature:lists")
include(":feature:lists:listsApi")
include(":feature:lists:listsUi")
include(":feature:bottomNavBar")
include(":feature:bottomNavBar:bottomNavBarUI")
include(":feature:bottomNavBar:bottomNavBarApi")
include(":safeimageviewer")
