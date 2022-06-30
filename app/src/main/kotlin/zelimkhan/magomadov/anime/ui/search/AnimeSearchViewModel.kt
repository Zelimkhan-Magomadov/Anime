package zelimkhan.magomadov.anime.ui.search

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import zelimkhan.magomadov.anime.data.anime.datasource.local.model.Anime
import zelimkhan.magomadov.anime.data.anime.repository.AnimeRepository
import zelimkhan.magomadov.anime.data.core.ErrorHandler
import zelimkhan.magomadov.anime.ui.core.extension.liveData
import zelimkhan.magomadov.anime.ui.search.model.AnimeItemUiState
import zelimkhan.magomadov.anime.ui.search.model.toItemUiState
import javax.inject.Inject

@HiltViewModel
class AnimeSearchViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel(), ErrorHandler {
    private val _userMessage = MutableLiveData<String?>()
    val userMessage = _userMessage.liveData

    val itemUiState = liveData {
        val source = animeRepository.fetchAnimeList().map { list: List<Anime> ->
            list.map { anime: Anime -> anime.toItemUiState() }
        }
        emitSource(source)
    }

    init {
        animeRepository.errorHandler = this
        viewModelScope.launch {
            animeRepository.deleteUnloaded()
        }
    }

    fun searchAnime(url: String) {
        viewModelScope.launch {
            animeRepository.searchAnime(url)
        }
    }

    override fun onNetworkError() {
        _userMessage.value = "Network error"
    }

    override fun onTimeoutError() {
        _userMessage.value = "Timeout error"
    }

    override fun onApiError(messageId: Int) {
        _userMessage.value = "Api error"
    }

    fun userMessageShown() {
        _userMessage.value = null
    }

    fun deleteSearchedAnime(items: List<AnimeItemUiState>) {
        viewModelScope.launch {
            val ids = items.map { it.id }
            animeRepository.deleteAll(ids)
            _userMessage.value = "Deleted ${items.size}"
        }
    }
}