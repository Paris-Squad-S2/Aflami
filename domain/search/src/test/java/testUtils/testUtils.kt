package testUtils

import com.domain.search.model.Media
import com.domain.search.model.MediaType
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun createMedia(
    id: Int,
    title: String,
    type: MediaType,
    rating: Double = 0.0,
    categories: List<String> = listOf(""),
    ): Media {
    return Media(
        id = id,
        imageUri = "image.com",
        title = title,
        type = type,
        categories = categories,
        yearOfRelease = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
        rating = rating,
    )
}
