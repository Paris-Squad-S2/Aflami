// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
    id("com.google.firebase.crashlytics") version "3.0.4" apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.google.firebase.appdistribution) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kover)
}

val koverExcludedPackages = listOf(
    "*.R",
    "*.R_*",
    "**.logging.**",
    "*.BuildConfig*",
    "*.Manifest*",
    "**.model.**",
    "**.models.**",
    "**.dto.**",
    "**.entity.**",
    "**.exception.**",
    "**.mapper.**",
    "**.util.**",
    "**.di.**",
    "**.implementation.**",
    "**.dao.**",
    "**.SearchConverter*",
    "**.SearchDatabase*",
    "**.NetworkConnectionChecker*",
    "**.navigation.**",
    "**.ui.theme.**",
    "**.MainActivity",
    "**.MainActivityKt",
    "**.MyClass",
    "**.designsystem.**",
    "**.BaseViewModel*",
    "**.*_Impl*",
    "*.ComposableSingletons*",
    "*.search.components.**",
    "*.searchUi.comon.**",
    "*.SearchApp*",
    "*ScreenKt",
    "**.ClearMediaWorker"
    /*
    "**.GenreResourceMapper",

 */
)

allprojects {
    apply(plugin = "org.jetbrains.kotlinx.kover")

    kover {
        reports {
            filters {
                excludes { classes(koverExcludedPackages) }
            }
        }
    }
}

dependencies {
    kover(project((":app")))
    kover(project((":domain:user")))
    kover(project((":domain:guessGame")))
    kover(project((":domain:media")))
    kover(project((":domain:lists")))
    kover(project((":domain:home")))
    kover(project((":domain:search")))
    kover(project((":domain:mediaDetails")))
    kover(project((":repository:user")))
    kover(project((":repository:movie")))
    kover(project((":repository:tvShow")))
    kover(project((":repository:lists")))
    kover(project((":repository:categories")))
    kover(project((":repository:guessGame")))
    kover(project((":repository:search")))
    kover(project((":datasource:remote:user")))
    kover(project((":datasource:remote:movie")))
    kover(project((":datasource:remote:tvShow")))
    kover(project((":datasource:remote:lists")))
    kover(project((":datasource:remote:categories")))
    kover(project((":datasource:remote:search")))
    kover(project((":datasource:local:user")))
    kover(project((":datasource:local:movie")))
    kover(project((":datasource:local:tvShow")))
    kover(project((":datasource:local:lists")))
    kover(project((":datasource:local:guessGame")))
    kover(project((":datasource:local:categories")))
    kover(project((":datasource:local:search")))
    kover(project((":feature:onboarding:onboardingApi")))
    kover(project((":feature:onboarding:onboardingUi")))
    kover(project((":feature:authentication:authenticationApi")))
    kover(project((":feature:authentication:authenticationUi")))
    kover(project((":feature:home:homeApi")))
    kover(project((":feature:home:homeUi")))
    kover(project((":feature:search:searchApi")))
    kover(project((":feature:search:searchUi")))
    kover(project((":feature:mediaDetails:mediaDetailsApi")))
    kover(project((":feature:mediaDetails:mediaDetailsUi")))
    kover(project((":feature:guessGame:guessGameApi")))
    kover(project((":feature:guessGame:guessGameUi")))
    kover(project((":feature:categories:categoriesApi")))
    kover(project((":feature:categories:categoriesUi")))
    kover(project((":feature:lists:listsApi")))
    kover(project((":feature:lists:listsUi")))
}