package com.repository.media.dto.profile

data class AccountRatedListDto(
    val page: Int = 0,
    val results: List<RatedMediaDto>,
    val total_pages: Int = 0,
    val total_results: Int = 0
)
