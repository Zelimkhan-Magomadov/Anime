package zelimkhan.magomadov.anime.data.anime.datasource.remote.model

import com.google.gson.annotations.SerializedName
import zelimkhan.magomadov.anime.data.anime.datasource.local.model.Anime

data class GetAnimeByImageResponse(
    @SerializedName("error") val error: String,
    @SerializedName("result") val resultList: List<SearchResult>
)

fun GetAnimeByImageResponse.toAnime(): Anime {
    val searchResult = this.resultList.first { !it.anilist.isAdult }
    return Anime(
        animeTitle = searchResult.anilist.title,
        episode = searchResult.episode,
        image = searchResult.image,
        video = searchResult.video,
        startTime = searchResult.startTime.toInt(),
    )
}

