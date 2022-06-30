package zelimkhan.magomadov.anime.ui.search.model

import zelimkhan.magomadov.anime.App
import zelimkhan.magomadov.anime.R
import zelimkhan.magomadov.anime.data.anime.datasource.local.model.Anime

data class AnimeItemUiState(
    val id: Long = 0,
    val animeTitle: String = "",
    val image: String = "",
    val video: String = "",
    val episode: String = "",
    val startTime: String = "",
    val isFavorite: Boolean = false,
    val isLoad: Boolean = false,
    val isLoaded: Boolean = true
)

fun Anime.toItemUiState(): AnimeItemUiState {
    fun getEpisode() = episode.ifEmpty { App.getString(R.string.movie) }

    fun getFormattedStartTime(): String {
        val hours = startTime / 3600
        val minutes = (startTime % 3600) / 60
        val seconds = startTime % 60
        return "%02d:%02d:%02d".format(hours, minutes, seconds)
    }

    return AnimeItemUiState(
        id = id,
        animeTitle = animeTitle,
        image = image,
        video = video,
        episode = getEpisode(),
        startTime = getFormattedStartTime(),
        isFavorite = isFavorite,
        isLoaded = isLoaded
    )
}