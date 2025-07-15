-keep class com.paris_2.aflami.designsystem.theme.AflamiThemeKt { *; }
-keep class com.paris_2.aflami.designsystem.theme.Theme { *; } # If 'Theme' is a class/object you define
-keep class com.paris_2.aflami.designsystem.color.** { *; } # Keep all classes in your color package
-keep class com.paris_2.aflami.designsystem.text_style.** { *; } # Keep all classes in your text_style package

-keep class com.paris_2.aflami.designsystem.components.**Kt { *; } # Keeps all Kotlin file classes (e.g., AflamiButtonKt)
-keep class com.paris_2.aflami.designsystem.components.**Kt$DefaultImpls { *; }
-keep class com.paris_2.aflami.designsystem.components.** { *; } # More general for any direct classes/interfaces
-keep class com.designSystem.safeimageviewer.SafeImageViewerKt { *; }
-keep class java.lang.invoke.** { *; }