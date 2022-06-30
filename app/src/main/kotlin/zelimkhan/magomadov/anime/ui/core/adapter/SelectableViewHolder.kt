package zelimkhan.magomadov.anime.ui.core.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView

abstract class SelectableViewHolder<T>(
    private val rootView: ViewGroup
) : RecyclerView.ViewHolder(rootView) {
    private val clickableViews = mutableListOf<View>()

    abstract fun select()

    abstract fun deselect()

    fun disableClickListeners() {
        recursivelyDisableListeners(rootView)
    }

    private fun recursivelyDisableListeners(viewGroup: ViewGroup) {
        viewGroup.forEach { view: View ->
            if (view is ViewGroup)
                recursivelyDisableListeners(view)
            if (view.isClickable) {
                clickableViews.add(view)
                view.isClickable = false
            }
        }
    }

    fun enableClickListeners() {
        clickableViews.forEach { it.isClickable = true }
    }

    abstract fun bind(data: T)
}
