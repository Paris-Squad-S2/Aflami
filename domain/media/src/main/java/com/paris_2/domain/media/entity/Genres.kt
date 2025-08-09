package com.paris_2.domain.media.entity

enum class Genre(
    val displayName: String
) {
    Action("Action"),
    Adventure( "Adventure"),
    Animation( "Animation"),
    Comedy( "Comedy"),
    Crime( "Crime"),
    Documentary( "Documentary"),
    Drama( "Drama"),
    Family("Family"),
    Fantasy( "Fantasy"),
    History( "History"),
    Horror( "Horror"),
    Music("Music"),
    Mystery( "Mystery"),
    Romance( "Romance"),
    ScienceFiction( "Science Fiction"),
    TVMovie( "TV Movie"),
    Thriller( "Thriller"),
    War( "War"),
    Western("Western"),
    ActionAdventure( "Action & Adventure"),
    Kids( "Kids"),
    News( "News"),
    Reality( "Reality"),
    SciFiFantasy("Sci-Fi & Fantasy"),
    Soap( "Soap"),
    Talk( "Talk"),
    WarPolitics( "War & Politics"),
    Unknown("Unknown");
}