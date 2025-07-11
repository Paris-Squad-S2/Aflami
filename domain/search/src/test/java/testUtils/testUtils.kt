package testUtils

import com.domain.search.model.Country
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
    categories: List<Int> = listOf(),
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

val sampleCountries = listOf(
    Country(countryCode = "1612", countryName = "United States"),
    Country(countryCode = "2113", countryName = "United Kingdom"),
    Country(countryCode = "1213", countryName = "Canada"),
    Country(countryCode = "3478", countryName = "Australia"),
    Country(countryCode = "3462", countryName = "Germany"),
    Country(countryCode = "1237", countryName = "France"),
    Country(countryCode = "9872", countryName = "Japan"),
    Country(countryCode = "2434", countryName = "Brazil"),
    Country(countryCode = "2312", countryName = "India"),
    Country(countryCode = "7484", countryName = "China")
)