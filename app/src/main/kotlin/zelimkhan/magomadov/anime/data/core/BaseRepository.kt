package zelimkhan.magomadov.anime.data.core

import retrofit2.HttpException
import zelimkhan.magomadov.anime.R
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseRepository {
    var errorHandler: ErrorHandler? = null

    protected suspend fun <T> safeApiCall(apiCall: suspend () -> T): T? {
        return try {
            apiCall.invoke()
        } catch (e: Exception) {
            when (e) {
                is IOException -> errorHandler?.onNetworkError()
                is SocketTimeoutException -> errorHandler?.onTimeoutError()
                is HttpException -> errorHandler?.onApiError(R.string.error)
            }
            null
        }
    }
}
