# Keep rules for Room Database components within this module
# These classes are likely referenced dynamically (e.g., by Koin, or Room's internal mechanisms)
# so R8 needs explicit instructions not to remove/obfuscate them.

# Keep the Room database class itself and its Companion object (if it exists)
-keep class com.datasource.local.search.SearchDatabase { *; }
-keep class com.datasource.local.search.SearchDatabase$Companion { *; }

# Keep all Data Access Objects (DAOs) within this package
-keep class com.datasource.local.search.dao.** { *; }

# Keep all Local Data Source implementations within this package
# These are the concrete implementations that Koin is trying to inject.
-keep class com.datasource.local.search.datasource.** { *; }

# Keep the Room entities used by this database if they reside within this module
# Make sure the package path is correct for your entity classes.
-keep class com.repository.search.entity.** { *; }

# Keep specific Room internal classes if they are being stripped
# This rule is often needed for Room's generated code.
-keepnames class * extends androidx.room.RoomDatabase

# Keep rule for StringConcatFactory, specifically needed by Room's generated code
-keep class com.datasource.local.search.SearchConverter { *; }