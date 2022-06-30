package zelimkhan.magomadov.anime.ui.core.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

val <T> MutableLiveData<T>.liveData get(): LiveData<T> = this