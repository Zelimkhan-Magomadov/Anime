package zelimkhan.magomadov.anime.ui.search.adapter

import android.graphics.Color
import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import zelimkhan.magomadov.anime.App
import zelimkhan.magomadov.anime.databinding.ItemAnimeBinding
import zelimkhan.magomadov.anime.ui.core.adapter.SelectableViewHolder
import zelimkhan.magomadov.anime.ui.search.model.AnimeItemUiState

class AnimeViewHolder(
    private val binding: ItemAnimeBinding
) : SelectableViewHolder<AnimeItemUiState>(binding.root) {
    private val defaultCardStrokeWidth = binding.root.strokeWidth
    private val defaultCardStrokeColor = binding.root.strokeColor
    private val proxy = App.getProxy()

    init {
        binding.video.setOnCompletionListener {
            binding.image.isVisible = true
        }

        binding.video.setOnClickListener {
            binding.video.start()
            binding.image.isVisible = false
        }
    }

    override fun select() {
        binding.root.strokeWidth = 8
        binding.root.strokeColor = Color.BLUE
    }

    override fun deselect() {
        binding.root.strokeWidth = defaultCardStrokeWidth
        binding.root.strokeColor = defaultCardStrokeColor
    }

    override fun bind(data: AnimeItemUiState) {
        if (!data.isLoaded)
            showProgress()
        else {
            hideProgress()
            binding.animeTitle.text = data.animeTitle
            binding.episode.text = data.episode
            binding.startTime.text = data.startTime
            binding.video.setVideoPath(proxy.getProxyUrl(data.video))
            binding.image.isVisible = true
            Glide.with(binding.root).load(data.image).into(binding.image)
        }
    }

    private fun showProgress() {
        binding.shimmer.shimmerLayout.showShimmer(true)
        binding.shimmer.shimmerLayout.isVisible = true
        binding.content.isVisible = false
    }

    private fun hideProgress() {
        binding.shimmer.shimmerLayout.stopShimmer()
        binding.shimmer.shimmerLayout.hideShimmer()
        binding.shimmer.shimmerLayout.visibility = View.GONE
        binding.content.isVisible = true
    }
}