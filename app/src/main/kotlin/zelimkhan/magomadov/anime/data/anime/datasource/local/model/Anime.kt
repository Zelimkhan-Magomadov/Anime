package zelimkhan.magomadov.anime.data.anime.datasource.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Anime(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val animeTitle: String = "",
    val image: String = "",
    val video: String = "",
    val episode: String = "",
    val startTime: Int = 0,
    val isFavorite: Boolean = false,
    val isLoaded: Boolean = true
)