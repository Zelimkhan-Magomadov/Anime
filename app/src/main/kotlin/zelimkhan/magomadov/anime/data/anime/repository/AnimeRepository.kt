package zelimkhan.magomadov.anime.data.anime.repository

import androidx.lifecycle.LiveData
import zelimkhan.magomadov.anime.data.anime.datasource.local.AnimeDao
import zelimkhan.magomadov.anime.data.anime.datasource.local.model.Anime
import zelimkhan.magomadov.anime.data.anime.datasource.remote.SearchAnimeApi
import zelimkhan.magomadov.anime.data.anime.datasource.remote.model.toAnime
import zelimkhan.magomadov.anime.data.core.BaseRepository

class AnimeRepository(
    private val localDataSource: AnimeDao,
    private val remoteDataSource: SearchAnimeApi
) : BaseRepository() {
    fun fetchAnimeList(): LiveData<List<Anime>> {
        return localDataSource.fetchAnimeList()
    }

    suspend fun searchAnime(url: String) {
        val animeId = localDataSource.save(Anime(isLoaded = false))
        val anime = safeApiCall { remoteDataSource.searchAnime(url).toAnime() }
        if (anime == null) localDataSource.delete(animeId)
        else localDataSource.save(anime.copy(id = animeId))
    }

    suspend fun delete(id: Long) {
        localDataSource.delete(id)
    }

    suspend fun deleteAll(ids: List<Long>) {
        localDataSource.deleteAll(ids)
    }

    suspend fun deleteUnloaded() {
        localDataSource.deleteUnloaded()
    }
}