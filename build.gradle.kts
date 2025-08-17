// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.google.firebase.appdistribution) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kover)
    alias(libs.plugins.hilt) apply false
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
    "**.*Converter*",
    "**.*Database*",
    "**.*DataBase*",
    "**.NetworkConnectionChecker*",
    "**.navigation.**",
    "**.ui.theme.**",
    "**.MainActivity",
    "**.MainActivityKt",
    "**.MyClass",
    "**.designSystem.**",
    "**.BaseViewModel*",
    "**.*_Impl*",
    "*.ComposableSingletons*",
    "*.search.components.**",
    "*.searchUi.comon.**",
    "*.SearchApp*",
    "*.AflamiApplication*",
    "**.components*",
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
    "**appnavigation**",
    "**.workmanager.**",
    "**ConcreteBaseViewModel.kt.**",
    "**.TvShowDetailsScreen.kt.**",
    "**.MovieDetailsScreen.kt.**",
    "**.shimmerable.kt.**",
    "**.HomeScreen*",
    "**.AflamiNavBar.**",
    "**.AflamiNavBarItem.**",
    "**bottomNavBar**",
    "**.ListDetailsViewModel*",
    "**.MediaUi*",
    "**.SimilarMediaUI.*",
    "**.MediaCardType.*",
    "**.HomeScreenViewModel*",
    "**.LoginScreen*",
    "**.SearchScreen*",
    "**.*Screen.*",
    "**.TopRatingMoviesScreen*",
    "**.ContinueWatchingScreen*",
    "**.HomeScreenUIState*",
    "**.RetrofitListApiService*",
    "**.ListDetailsViewModel*",
    "**.MovieDetailsScreen*",
    "**.MovieUiState.*",
    "**/pagging*",
    "**MovieDetailsViewModel.kt*",
    "**AuthInterceptor*",
    "**.LanguageLocalDataSourceRepositoryImp.*",
    "**.LanguageRepositoryImp.*",
    "**.ChangePasswordViewModel.*",
    "**.profileUi.screen.**",
    "**.InstallSavedAppLanguage*",
    "**.PagingSource*",
    "**.MainViewModel*",
    "**.*_Factory*",
    "**.*_HiltModules*",
    "**.*_HiltModules_*"
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
    kover(projects.app)
    kover(projects.domain.user)
    kover(projects.domain.guessGame)
    kover(projects.domain.media)
    kover(projects.repository.movie)
    kover(projects.repository.lists)
    kover(projects.repository.guessGame)
    kover(projects.datasource.remote.user)
    kover(projects.datasource.remote.lists)
    kover(projects.datasource.remote.categories)
    kover(projects.datasource.remote.media)
    kover(projects.datasource.local.guessGame)
    kover(projects.datasource.local.categories)
    kover(projects.datasource.local.media)
    kover(projects.datasource.local.user)
    kover(projects.feature.onboarding.onboardingUi)
    kover(projects.feature.authentication.authenticationUi)
    kover(projects.feature.home.homeUi)
    kover(projects.feature.search.searchUi)
    kover(projects.feature.mediaDetails.mediaDetailsUi)
    kover(projects.feature.guessGame.guessGameUi)
    kover(projects.feature.categories.categoriesUi)
    kover(projects.feature.lists.listsApi)
}