package zelimkhan.magomadov.anime.data.anime.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class Title(
    @SerializedName("native") val native: String?,
    @SerializedName("romaji") val romaji: String?,
    @SerializedName("english") val english: String?
)