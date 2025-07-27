import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.feature.search.searchUi.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate

data class SearchScreenState(
    val searchUiState: SearchUiState = SearchUiState(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)


data class SearchUiState(
    val searchQuery: String = "",
    val showFilterDialog: Boolean = false,
    val recentSearches: List<SearchHistoryUiState> = listOf(),
    val selectedTabIndex: Int = 0,
    val moviesResult: Flow<PagingData<MediaUiState>> = flowOf(PagingData.empty()),
    val tvShowsResult: Flow<PagingData<MediaUiState>> = flowOf(PagingData.empty()),
    val filteredMoviesResult: Flow<PagingData<MediaUiState>> = flowOf(PagingData.empty()),
    val filteredTvShowsResult: Flow<PagingData<MediaUiState>> = flowOf(PagingData.empty()),
    val categories: Map<CategoryUiState, Boolean> = mapOf(),
    val selectedRating: Float = 0f,
    val isAllCategories: Boolean = true,
    val isApplyFilter: Boolean = false,
)


data class MediaUiState(
    val id: Int,
    val imageUri: String,
    val title: String,
    val type: MediaTypeUi,
    val categories: List<Int>,
    val yearOfRelease: LocalDate,
    val rating: Double,
)

enum class MediaTypeUi(val mediaName: String) {
    TVSHOW("TV Show"),
    MOVIE("Movie")
}

data class CategoryUiState(
    val id: Int,
    val name: String,
)

data class SearchHistoryUiState(
    val searchTitle: String,
    val searchDate: String,
    val searchType: SearchTypeUi,
)

enum class SearchTypeUi(val displayNameResId: Int) {
    Query(R.string.query),
    Country(R.string.country),
    Actor(R.string.actor);

}

class NoopListUpdateCallback : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}