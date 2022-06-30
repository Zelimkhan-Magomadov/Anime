package zelimkhan.magomadov.anime.ui.core.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class SelectableAdapter<T, VH : SelectableViewHolder<T>>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffCallback) {
    interface SelectionModeListener {
        fun onEnableMode(count: Int)

        fun onToggleSelection(count: Int)

        fun onAllSelected()

        fun onDisableMode()
    }

    private val viewHolders = mutableListOf<VH>()
    private val posOfSelectedVH = hashSetOf<Int>()
    private val selectedItemsCount get() = posOfSelectedVH.count()
    private var isSelectionModeEnabled = false
    var selectionModeListener: SelectionModeListener? = null
    val selectedItems get() = posOfSelectedVH.map { getItem(it) }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (isSelected(holder)) holder.select()
        else holder.deselect()
    }

    private fun isSelected(vh: VH) = posOfSelectedVH.contains(vh.adapterPosition)

    protected fun setSelectable(vh: VH, view: View, onClick: () -> Unit) {
        setOnClickListener(view, vh, onClick)
        setOnLongClickListener(view, vh)
        addViewHolder(vh)
    }

    private fun setOnClickListener(view: View, vh: VH, onClick: () -> Unit) {
        view.setOnClickListener {
            if (isSelectionModeEnabled) toggleSelection(vh)
            else onClick.invoke()
        }
    }

    private fun setOnLongClickListener(view: View, vh: VH) {
        view.setOnLongClickListener {
            toggleSelection(vh)
            true
        }
    }

    private fun addViewHolder(vh: VH) {
        viewHolders.add(vh)
    }

    private fun toggleSelection(vh: VH) {
        if (isSelected(vh)) deselectItem(vh)
        else selectItem(vh)
        selectionModeListener?.onToggleSelection(selectedItemsCount)
        if (selectedItems.size == itemCount)
            selectionModeListener?.onAllSelected()
    }

    private fun deselectItem(vh: VH) {
        posOfSelectedVH.remove(vh.adapterPosition)
        vh.deselect()
        if (selectedItemsCount == 0) {
            isSelectionModeEnabled = false
            selectionModeListener?.onDisableMode()
            enableClickListeners()
        }
    }

    private fun enableClickListeners() {
        viewHolders.forEach { it.enableClickListeners() }
    }

    private fun selectItem(vh: VH) {
        posOfSelectedVH.add(vh.adapterPosition)
        vh.select()
        if (!isSelectionModeEnabled) {
            isSelectionModeEnabled = true
            selectionModeListener?.onEnableMode(selectedItemsCount)
            disableClickListeners()
        }
    }

    private fun disableClickListeners() {
        viewHolders.forEach { it.disableClickListeners() }
    }

    fun selectAll() {
        posOfSelectedVH.addAll(0.until(itemCount))
        viewHolders.forEach { it.select() }
        selectionModeListener?.onToggleSelection(selectedItemsCount)
        selectionModeListener?.onAllSelected()
    }

    fun deselectAll() {
        posOfSelectedVH.clear()
        viewHolders.forEach { it.deselect() }
        selectionModeListener?.onToggleSelection(selectedItemsCount)
    }

    fun disableSelectionMode() {
        enableClickListeners()
        viewHolders.forEach { viewHolder -> viewHolder.deselect() }
        posOfSelectedVH.clear()
        isSelectionModeEnabled = false
    }
}