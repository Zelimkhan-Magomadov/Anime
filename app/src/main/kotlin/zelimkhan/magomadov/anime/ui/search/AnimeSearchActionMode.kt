package zelimkhan.magomadov.anime.ui.search

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import zelimkhan.magomadov.anime.R

class AnimeSearchActionMode : ActionMode.Callback {
    interface Listener {
        fun onClickDeleteItem()

        fun onClickSelectAllItem()

        fun onClickDeselectAllItem()

        fun onDestroy()
    }

    private var actionMode: ActionMode? = null
    var listener: Listener? = null
    private var deleteItem: MenuItem? = null
    private var selectAllItem: MenuItem? = null
    private var deselectAllItem: MenuItem? = null

    fun start(view: View, count: Int) {
        actionMode = view.startActionMode(this)
        setTitle(count)
    }

    fun cancel() {
        actionMode?.finish()
    }

    fun setTitle(count: Int) {
        actionMode?.title = count.toString()
        if (count > 0) {
            deleteItem?.isEnabled = true
            hideDeselectAllItem()
        } else deleteItem?.isEnabled = false
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.anime_search_action_mode, menu)
        setupMenuItems(mode.menu)
        return true
    }

    private fun setupMenuItems(menu: Menu) {
        deleteItem = menu.findItem(R.id.action_delete)
        selectAllItem = menu.findItem(R.id.action_select_all)
        deselectAllItem = menu.findItem(R.id.action_deselect_all)
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                listener?.onClickDeleteItem()
                mode.finish()
            }
            R.id.action_select_all -> {
                listener?.onClickSelectAllItem()
                hideSelectAllItem()
            }
            R.id.action_deselect_all -> {
                listener?.onClickDeselectAllItem()
                hideDeselectAllItem()
            }
        }
        return true
    }

    fun hideSelectAllItem() {
        selectAllItem?.isVisible = false
        deselectAllItem?.isVisible = true
    }

    fun hideDeselectAllItem() {
        selectAllItem?.isVisible = true
        deselectAllItem?.isVisible = false
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        listener?.onDestroy()
        actionMode = null
    }
}