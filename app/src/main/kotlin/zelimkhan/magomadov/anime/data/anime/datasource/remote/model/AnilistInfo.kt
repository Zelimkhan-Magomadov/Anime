package zelimkhan.magomadov.anime.data.anime.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class AnilistInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("title") private val _title: Title,
    @SerializedName("isAdult") val isAdult: Boolean
) {
    val title get() = _title.english ?: _title.romaji ?: _title.native ?: ""
}
