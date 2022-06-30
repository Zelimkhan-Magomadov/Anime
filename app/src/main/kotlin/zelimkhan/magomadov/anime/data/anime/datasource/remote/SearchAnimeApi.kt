package zelimkhan.magomadov.anime.data.anime.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Query
import zelimkhan.magomadov.anime.data.anime.datasource.remote.model.GetAnimeByImageResponse

interface SearchAnimeApi {
    @GET("search?anilistInfo")
    suspend fun searchAnime(@Query("url") url: String): GetAnimeByImageResponse
}