package zelimkhan.magomadov.anime.ui.core.extension

import android.util.Patterns
import android.webkit.URLUtil

val CharSequence?.isValidURL
    get() = URLUtil.isValidUrl(this.toString()) && Patterns.WEB_URL.matcher(this.toString()).matches()