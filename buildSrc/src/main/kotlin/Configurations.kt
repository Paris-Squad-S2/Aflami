import org.gradle.api.JavaVersion

object Configurations {
    const val COMPILE_SDK = 35
    const val MIN_SDK = 26
    const val TARGET_SDK = 35
    const val VERSION_CODE = 1
    const val JVM_TARGET = "11"
    const val KOTLIN_COMPILER = "1.5.4"

    const val NAME_SPACE_AFLAMI = "com.paris_2.aflami"

    const val NAME_SPACE_FEATURE_HOME_API = "com.feature.home.homeApi"
    const val NAME_SPACE_FEATURE_HOME_UI = "com.feature.home.homeUi"
    const val NAME_SPACE_FEATURE_BOTTOM_NAV_BAR_UI =
        "com.paris_2.aflami.bottomNavBar.bottomNavBarUI"
    const val NAME_SPACE_FEATURE_AUTH_UI = "com.feature.authentication.authenticationUi"
    const val NAME_SPACE_FEATURE_CATEGORIES_API = "com.feature.categories.categoriesApi"
    const val NAME_SPACE_FEATURE_CATEGORIES_UI = "com.feature.categories.categoriesUi"
    const val NAME_SPACE_FEATURE_GUESS_GAME_API = "com.feature.guessGame.guessGameApi"
    const val NAME_SPACE_FEATURE_GUESS_GAME_UI = "com.feature.guessGame.guessGameUi"
    const val NAME_SPACE_FEATURE_LIST_API = "com.feature.lists.listsApi"
    const val NAME_SPACE_FEATURE_LIST_UI = "com.feature.lists.listsUi"
    const val NAME_SPACE_FEATURE_ONBOARDING_UI = "com.feature.onboarding.onboardingUi"
    const val NAME_SPACE_FEATURE_MEDIA_DETAILS_UI = "com.feature.mediaDetails.mediaDetailsUi"
    const val NAME_SPACE_FEATURE_PROFILE_API = "com.feature.profile.profileApi"
    const val NAME_SPACE_FEATURE_PROFILE_UI = "com.feature.profile.profileUi"
    const val NAME_SPACE_FEATURE_SEARCH_UI = "com.feature.search.searchUi"

    const val NAME_SPACE_REPOSITORY_USER = "com.paris_2.repository.user"
    const val NAME_SPACE_REPOSITORY_MEDIA = "com.repository.media"
    const val NAME_SPACE_REPOSITORY_MOVIE = "com.repository.movie"
    const val NAME_SPACE_REPOSITORY_TVSHOW = "com.repository.tvshow"
    const val NAME_SPACE_REPOSITORY_LIST = "com.paris.repository.list"

    const val NAME_SPACE_SAVE_IMAGE_VIEWER = "com.designSystem.safeimageviewer"

    const val NAME_SPACE_LOGGER = "com.parise_2.logger"

    const val NAME_SPACE_DESIGN_SYSTEM = "com.paris_2.aflami.designsystem"

    const val NAME_SPACE_LOCAL_MEDIA = "com.datasource.local.media"
    const val NAME_SPACE_LOCAL_MOVIE = "com.datasource.local.movie"
    const val NAME_SPACE_LOCAL_TVSHOW = "com.datasource.local.tvshow"
    const val NAME_SPACE_LOCAL_USER = "com.paris_2.dataSource.local.user"

    const val NAME_SPACE_REMOTE_MOVIE = "com.datasource.remote.movie"
    const val NAME_SPACE_REMOTE_TVSHOW = "com.datasource.remote.tvShow"
    const val NAME_SPACE_REMOTE_USER = "com.paris_2.datasource.remote.user"
    const val NAME_SPACE_REMOTE_MEDIA = "com.paris_2.media"
    const val NAME_SPACE_REMOTE_LISTS = "com.paris.datasource.remote.lists"

    @JvmField
    val JAVA_VERSION = JavaVersion.VERSION_11
}