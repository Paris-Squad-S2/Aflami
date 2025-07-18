package com.feature.categories.categoriesApi

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
sealed interface CategoriesDestination

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
 fun CategoriesDestination.toJson(): String =
    Json.encodeToString(CategoriesDestination::class.serializer(), this)

 fun String.fromJsonToCategoriesDestination(): CategoriesDestination =
    Json.decodeFromString(this)