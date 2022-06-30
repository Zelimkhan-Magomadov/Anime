package zelimkhan.magomadov.anime

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import com.danikula.videocache.HttpProxyCacheServer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        private lateinit var context: Context
        private var PROXY_INSTANCE: HttpProxyCacheServer? = null

        fun getString(@StringRes id: Int): String {
            return context.getString(id)
        }

        fun getProxy(): HttpProxyCacheServer {
            if (PROXY_INSTANCE == null)
                PROXY_INSTANCE = HttpProxyCacheServer
                    .Builder(context)
                    .maxCacheSize(52428800)
                    .maxCacheFilesCount(99)
                    .build()
            return PROXY_INSTANCE!!
        }
    }
}