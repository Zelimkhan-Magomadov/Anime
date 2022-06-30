package zelimkhan.magomadov.anime.data.core

import androidx.annotation.StringRes

interface ErrorHandler {
    fun onNetworkError()

    fun onTimeoutError()

    fun onApiError(@StringRes messageId: Int)
}