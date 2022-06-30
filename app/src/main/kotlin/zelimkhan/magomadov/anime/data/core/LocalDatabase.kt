package zelimkhan.magomadov.anime.data.core

import androidx.room.Database
import androidx.room.RoomDatabase
import zelimkhan.magomadov.anime.data.anime.datasource.local.AnimeDao
import zelimkhan.magomadov.anime.data.anime.datasource.local.model.Anime

@Database(entities = [Anime::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract val animeDao: AnimeDao

    companion object {
        const val DATABASE_NAME = "anime_db"
    }
}