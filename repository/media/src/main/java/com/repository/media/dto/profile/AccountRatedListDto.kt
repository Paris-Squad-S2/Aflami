package com.repository.media.dto.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountRatedListDto(
    @SerialName("page")
    val page: Int = 0,
    @SerialName("results")
    val results: List<RatedMediaDto>,
    @SerialName("total_pages")
    val total_pages: Int = 0,
    @SerialName("total_results")
    val total_results: Int = 0
)
