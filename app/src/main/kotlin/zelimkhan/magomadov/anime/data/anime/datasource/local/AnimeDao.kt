package zelimkhan.magomadov.anime.data.anime.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.*
import zelimkhan.magomadov.anime.data.anime.datasource.local.model.Anime

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(anime: Anime): Long

    @Query("DELETE FROM anime WHERE id =:id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM anime WHERE id IN (:ids)")
    suspend fun deleteAll(ids: List<Long>)

    @Query("SELECT * FROM anime ORDER BY id DESC")
    fun fetchAnimeList(): LiveData<List<Anime>>

    @Query("SELECT * FROM anime WHERE id = :id")
    fun fetchAnime(id: Long): Anime

    @Query("DELETE FROM anime WHERE isLoaded = 0")
    suspend fun deleteUnloaded()
}