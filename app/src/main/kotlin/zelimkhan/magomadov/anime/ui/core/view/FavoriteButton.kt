package zelimkhan.magomadov.anime.ui.core.view

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.getResourceIdOrThrow
import zelimkhan.magomadov.anime.R

class FavoriteButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    var isFavorite = false
        private set

    @DrawableRes
    private val favoriteResId: Int

    @DrawableRes
    private val nonFavoriteResId: Int

    init {
        isSaveEnabled = true

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.FavoriteButton,
            defStyleAttr,
            0
        )
        favoriteResId =
            typedArray.getResourceIdOrThrow(R.styleable.FavoriteButton_favourite_drawable)
        nonFavoriteResId =
            typedArray.getResourceIdOrThrow(R.styleable.FavoriteButton_non_favourite_drawable)
        setImageResource(nonFavoriteResId)
        typedArray.recycle()
    }

    override fun performClick(): Boolean {
        isFavorite = if (isFavorite) {
            setImageResource(nonFavoriteResId)
            false
        } else {
            setImageResource(favoriteResId)
            true
        }
        return super.performClick()
    }
}