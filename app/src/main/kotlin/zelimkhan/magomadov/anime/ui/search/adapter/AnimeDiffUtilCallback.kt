package zelimkhan.magomadov.anime.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import zelimkhan.magomadov.anime.ui.search.model.AnimeItemUiState

class AnimeDiffUtilCallback : DiffUtil.ItemCallback<AnimeItemUiState>() {
    override fun areItemsTheSame(oldItem: AnimeItemUiState, newItem: AnimeItemUiState): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AnimeItemUiState, newItem: AnimeItemUiState): Boolean {
        return oldItem == newItem
    }
}