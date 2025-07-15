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
-dontwarn com.paris_2.aflami.designsystem.color.Colors
-dontwarn com.paris_2.aflami.designsystem.color.Gradient
-dontwarn com.paris_2.aflami.designsystem.color.TextColors
-dontwarn com.paris_2.aflami.designsystem.components.AflamiButtonKt
-dontwarn com.paris_2.aflami.designsystem.components.AflamiDialogKt
-dontwarn com.paris_2.aflami.designsystem.components.AflamiHorizontalDividerKt
-dontwarn com.paris_2.aflami.designsystem.components.AflamiMediaCardKt
-dontwarn com.paris_2.aflami.designsystem.components.AflamiTabsKt
-dontwarn com.paris_2.aflami.designsystem.components.AflamiTextKt
-dontwarn com.paris_2.aflami.designsystem.components.ButtonState
-dontwarn com.paris_2.aflami.designsystem.components.ButtonType$Primary
-dontwarn com.paris_2.aflami.designsystem.components.ButtonType$Secondary
-dontwarn com.paris_2.aflami.designsystem.components.ButtonType$TextButton
-dontwarn com.paris_2.aflami.designsystem.components.ButtonType
-dontwarn com.paris_2.aflami.designsystem.components.ChipsKt
-dontwarn com.paris_2.aflami.designsystem.components.IconItem
-dontwarn com.paris_2.aflami.designsystem.components.MediaCardType
-dontwarn com.paris_2.aflami.designsystem.components.NetworkErrorKt
-dontwarn com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolderKt
-dontwarn com.paris_2.aflami.designsystem.components.PlaceholderViewKt
-dontwarn com.paris_2.aflami.designsystem.components.RatingBarKt
-dontwarn com.paris_2.aflami.designsystem.components.RecentSearchItemKt
-dontwarn com.paris_2.aflami.designsystem.components.SearchSuggestionHubKt
-dontwarn com.paris_2.aflami.designsystem.components.TextFieldsKt
-dontwarn com.paris_2.aflami.designsystem.components.TopAppBarKt
-dontwarn com.paris_2.aflami.designsystem.text_style.AflamiTextStyle
-dontwarn com.paris_2.aflami.designsystem.text_style.SizedTextStyle
-dontwarn com.paris_2.aflami.designsystem.theme.AflamiThemeKt
-dontwarn com.paris_2.aflami.designsystem.theme.Theme
-dontwarn org.slf4j.impl.StaticLoggerBinder
-keep class java.lang.invoke.StringConcatFactory { *; }
-keep class java.lang.invoke.StringConcatException { *; }