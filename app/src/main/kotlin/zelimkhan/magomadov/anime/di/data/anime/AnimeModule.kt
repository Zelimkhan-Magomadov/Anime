package zelimkhan.magomadov.anime.di.data.anime

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import zelimkhan.magomadov.anime.data.anime.datasource.local.AnimeDao
import zelimkhan.magomadov.anime.data.anime.datasource.remote.SearchAnimeApi
import zelimkhan.magomadov.anime.data.anime.repository.AnimeRepository
import zelimkhan.magomadov.anime.data.core.ErrorHandler
import zelimkhan.magomadov.anime.data.core.LocalDatabase
import zelimkhan.magomadov.anime.ui.search.AnimeSearchViewModel
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ViewModelComponent::class)
class AnimeModule {
    @Provides
    fun provideRepository(
        localDataSource: AnimeDao,
        remoteDataSource: SearchAnimeApi
    ): AnimeRepository {
        return AnimeRepository(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )
    }

    @Provides
    fun provideLocalDataSource(database: LocalDatabase): AnimeDao {
        return database.animeDao
    }

    @Provides
    fun provideRemoteDataSource(okHttpClient: OkHttpClient): SearchAnimeApi {
        return Retrofit.Builder()
            .baseUrl("https://api.trace.moe/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(SearchAnimeApi::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .callTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}