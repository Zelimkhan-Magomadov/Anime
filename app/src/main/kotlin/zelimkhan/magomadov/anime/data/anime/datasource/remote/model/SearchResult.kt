package zelimkhan.magomadov.anime.data.anime.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("anilist") val anilist: AnilistInfo,
    @SerializedName("episode") private val _episode: Any?,
    @SerializedName("from") val startTime: Double,
    @SerializedName("video") private val _video: String,
    @SerializedName("image") private val _image: String
) {
    val episode get() = (_episode ?: "").toString().substringBeforeLast('.')
    val image get() = "${_image}&size=l"
    val video get() = "${_video}&size=l"
}
