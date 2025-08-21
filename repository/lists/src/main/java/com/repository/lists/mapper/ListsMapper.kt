package com.repository.lists.mapper

import com.paris.domain.lists.entity.Lists
import com.repository.lists.model.dto.ListsDto

fun ListsDto.toDomain(): List<Lists> {
    return listDto?.map {
        Lists(
            id = it.id ?: 0,
            name = it.name.orEmpty(),
            description = it.description.orEmpty(),
            itemCount = it.itemCount ?: 0
        )
    } ?: emptyList()
}

//fun ResponseDto.toDomain(): Response {
//    return Response(
//        statusCode = this.statusCode?: 0,
//        statusMessage = this.statusMessage.orEmpty(),
//        success = this.success  ?: false
//    )
//}