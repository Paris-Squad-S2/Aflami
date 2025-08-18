package com.repository.guessgame.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ActorPopularityListDto(

	@SerialName("page")
	val page: Int? = null,

	@SerialName("total_pages")
	val totalPages: Int? = null,

	@SerialName("results")
	val results: List<ActorDto?>? = null,

	@SerialName("total_results")
	val totalResults: Int? = null
)