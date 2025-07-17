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
    Country(countryCode = "1612", englishName = "United States", arabicName = "الولايات المتحدة"),
    Country(countryCode = "2113", englishName = "United Kingdom", arabicName = "المملكة المتحدة"),
    Country(countryCode = "1213", englishName = "Canada", arabicName = "كندا"),
    Country(countryCode = "3478", englishName = "Australia", arabicName = "أستراليا"),
    Country(countryCode = "3462", englishName = "Germany", arabicName = "ألمانيا"),
    Country(countryCode = "1237", englishName = "France", arabicName = "فرنسا"),
    Country(countryCode = "9872", englishName = "Japan", arabicName = "اليابان"),
    Country(countryCode = "2434", englishName = "Brazil", arabicName = "البرازيل"),
    Country(countryCode = "2312", englishName = "India", arabicName = "الهند"),
    Country(countryCode = "7484", englishName = "China", arabicName = "الصين")
)