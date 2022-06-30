package zelimkhan.magomadov.anime.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import zelimkhan.magomadov.anime.databinding.ItemAnimeBinding
import zelimkhan.magomadov.anime.ui.core.adapter.SelectableAdapter
import zelimkhan.magomadov.anime.ui.core.adapter.SelectableViewHolder
import zelimkhan.magomadov.anime.ui.search.model.AnimeItemUiState

class AnimeAdapter : SelectableAdapter<AnimeItemUiState, SelectableViewHolder<AnimeItemUiState>>(
    AnimeDiffUtilCallback()
) {
    interface ActionListener {
        fun onAnimeClick(id: Long)

        fun onClickFavoriteButton()
    }

    var actionListener: ActionListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectableViewHolder<AnimeItemUiState> {
        val binding = ItemAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnimeViewHolder(binding).apply {
            setSelectable(this, binding.root) {
                val id = getItem(adapterPosition).id
                actionListener?.onAnimeClick(id)
            }
        }
    }

    override fun onBindViewHolder(holder: SelectableViewHolder<AnimeItemUiState>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(getItem(position))
    }
}