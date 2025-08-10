import androidx.paging.PagingData
import com.feature.search.searchUi.R
import com.paris_2.domain.media.entity.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

data class SearchScreenState(
    val searchUiState: SearchUiState,
    val isLoading: Boolean,
    val errorMessage: String?
)


data class SearchUiState(
    val searchQuery: String,
    val showFilterDialog: Boolean,
    val recentSearches: List<SearchHistoryUiState>,
    val selectedTabIndex: Int,
    val moviesResult: Flow<PagingData<MediaUiState>>,
    val tvShowsResult: Flow<PagingData<MediaUiState>>,
    val filteredMoviesResult: Flow<PagingData<MediaUiState>>,
    val filteredTvShowsResult: Flow<PagingData<MediaUiState>>,
    val categories: Map<Category, Boolean>,
    val selectedRating: Float,
    val isAllCategories: Boolean,
    val isApplyFilter:Boolean,
)


data class MediaUiState(
    val id: Int,
    val imageUri: String,
    val title: String,
    val type: MediaTypeUi,
    val categories: List<Category>,
    val yearOfRelease: LocalDate,
    val rating: Double?,
)

enum class MediaTypeUi(val mediaName: String) {
    TVSHOW("TV Show"),
    MOVIE("Movie")
}
data class SearchHistoryUiState(
    val searchTitle: String,
    val searchDate: String,
    val searchType: SearchTypeUi
)

enum class SearchTypeUi(val displayNameResId: Int) {
    Query(R.string.query),
    Country(R.string.country),
    Actor(R.string.actor);
}
