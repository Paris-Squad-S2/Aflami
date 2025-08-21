# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn com.paris.aflami.designsystem.color.Colors
-dontwarn com.paris.aflami.designsystem.color.Gradient
-dontwarn com.paris.aflami.designsystem.color.TextColors
-dontwarn com.paris.aflami.designsystem.components.CustomButtonKt
-dontwarn com.paris.aflami.designsystem.components.AppDialogKt
-dontwarn com.paris.aflami.designsystem.components.AppHorizontalDividerKt
-dontwarn com.paris.aflami.designsystem.components.MediaCardKt
-dontwarn com.paris.aflami.designsystem.components.CustomTabKt
-dontwarn com.paris.aflami.designsystem.components.AppTextKt
-dontwarn com.paris.aflami.designsystem.components.ButtonState
-dontwarn com.paris.aflami.designsystem.components.ButtonType$Primary
-dontwarn com.paris.aflami.designsystem.components.ButtonType$Secondary
-dontwarn com.paris.aflami.designsystem.components.ButtonType$TextButton
-dontwarn com.paris.aflami.designsystem.components.ButtonType
-dontwarn com.paris.aflami.designsystem.components.ChipsKt
-dontwarn com.paris.aflami.designsystem.components.IconItem
-dontwarn com.paris.aflami.designsystem.components.MediaCardType
-dontwarn com.paris.aflami.designsystem.components.NetworkErrorKt
-dontwarn com.paris.aflami.designsystem.components.PageLoadingPlaceHolderKt
-dontwarn com.paris.aflami.designsystem.components.PlaceholderViewKt
-dontwarn com.paris.aflami.designsystem.components.RatingBarKt
-dontwarn com.paris.aflami.designsystem.components.RecentSearchItemKt
-dontwarn com.paris.aflami.designsystem.components.SearchSuggestionHubKt
-dontwarn com.paris.aflami.designsystem.components.AppTextFieldsKt
-dontwarn com.paris.aflami.designsystem.components.AppTopBarKt
-dontwarn com.paris.aflami.designsystem.text_style.AflamiTextStyle
-dontwarn com.paris.aflami.designsystem.text_style.SizedTextStyle
-dontwarn com.paris.aflami.designsystem.theme.AflamiThemeKt
-dontwarn com.paris.aflami.designsystem.theme.Theme
-dontwarn org.slf4j.impl.StaticLoggerBinder
-keep class com.feature.guessGame.guessGameUi.navigation.QuestionType { *; }
-keep class com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel { *; }