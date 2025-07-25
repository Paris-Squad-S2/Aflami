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
    "**.navigation.**",
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
    "**.components**",
    "*ScreenKt",
    "**.ClearMediaWorker",
    "**FeatureAPI**",
    "**NavGraph**",
    "**Destination**",
    "**Composable**",
    "*Screen.kt",
    "**State**",
    "**UiState**",
    "**Mapper**",
    "**Exception**",
    "**SafeApiCall**",
    "**ApiServiceImpl**",
    "**.AflamiNavBar",
    "**.AppScafold",
    "**/app**",
    "AflamiNavBar.kt",
    "AppScafold.kt",
    "**Screen.kt",
    "**Activity**",
    "**Navigation**",
    "**appnavigation**",
    "**appNavigation**",
    "**.HomeConverter*",
    "**.HomeDatabase*",
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
    kover(project((Modules.APP)))
    kover(project((Modules.DOMAIN_USER)))
    kover(project((Modules.DOMAIN_GUESS_GAME)))
    kover(project((Modules.DOMAIN_MEDIA)))
    kover(project((Modules.DOMAIN_LISTS)))
    kover(project((Modules.DOMAIN_HOME)))
    kover(project((Modules.DOMAIN_SEARCH)))
    kover(project((Modules.DOMAIN_CATEGORIES)))
    kover(project((Modules.DOMAIN_MEDIA_DETAILS)))
    kover(project((Modules.REPOSITORY_USER)))
    kover(project((Modules.REPOSITORY_MOVIE)))
    kover(project((Modules.REPOSITORY_MOVIE)))
    kover(project((Modules.REPOSITORY_LISTS)))
    kover(project((Modules.REPOSITORY_CATEGORIES)))
    kover(project((Modules.REPOSITORY_GUESS_GAME)))
    kover(project((Modules.REPOSITORY_SEARCH)))
    kover(project((Modules.DATASOURCE_REMOTE_USER)))
    kover(project((Modules.DATASOURCE_REMOTE_LISTS)))
    kover(project((Modules.DATASOURCE_REMOTE_CATEGORIES)))
    kover(project((Modules.DATASOURCE_REMOTE_SEARCH)))
    kover(project((Modules.DATASOURCE_LOCAL_USER)))
    kover(project((Modules.DATASOURCE_LOCAL_MOVIE)))
    kover(project((Modules.DATASOURCE_LOCAL_TV_SHOW)))
    kover(project((Modules.DATASOURCE_LOCAL_LISTS)))
    kover(project((Modules.DATASOURCE_LOCAL_GUESS_GAME)))
    kover(project((Modules.DATASOURCE_LOCAL_CATEGORIES)))
    kover(project((Modules.DATASOURCE_LOCAL_SEARCH)))
    kover(project((Modules.FEATURE_ONBOARDING_API)))
    kover(project((Modules.FEATURE_ONBOARDING_UI)))
    kover(project((Modules.FEATURE_AUTHENTICATION_API)))
    kover(project((Modules.FEATURE_AUTHENTICATION_UI)))
    kover(project((Modules.FEATURE_HOME_API)))
    kover(project((Modules.FEATURE_HOME_UI)))
    kover(project((Modules.FEATURE_SEARCH_API)))
    kover(project((Modules.FEATURE_SEARCH_UI)))
    kover(project((Modules.FEATURE_MEDIA_DETAILS_API)))
    kover(project((Modules.FEATURE_MEDIA_DETAILS_UI)))
    kover(project((Modules.FEATURE_GUESS_GAME_API)))
    kover(project((Modules.FEATURE_GUESS_GAME_UI)))
    kover(project((Modules.FEATURE_CATEGORIES_API)))
    kover(project((Modules.FEATURE_CATEGORIES_UI)))
    kover(project((Modules.FEATURE_LISTS_API)))
    kover(project((Modules.FEATURE_LISTS_UI)))
}