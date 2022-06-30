package zelimkhan.magomadov.anime.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import zelimkhan.magomadov.anime.R
import zelimkhan.magomadov.anime.databinding.FragmentAnimeSearchBinding
import zelimkhan.magomadov.anime.ui.core.adapter.SelectableAdapter
import zelimkhan.magomadov.anime.ui.core.extension.connectToolbarToNavigationController
import zelimkhan.magomadov.anime.ui.search.adapter.AnimeAdapter
import zelimkhan.magomadov.anime.ui.search.dialog.InputURLDialog
import zelimkhan.magomadov.anime.ui.search.dialog.SelectingResourceTypeDialog

@AndroidEntryPoint
class AnimeSearchFragment : Fragment(R.layout.fragment_anime_search) {
    private lateinit var binding: FragmentAnimeSearchBinding
    private val viewModel: AnimeSearchViewModel by viewModels()
    private val animeAdapter = AnimeAdapter()
    private var actionMode = AnimeSearchActionMode()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnimeSearchBinding.bind(view)
        connectToolbarToNavigationController(binding.appbar.toolbar)
        setupResultListeners()
        setupActionMode()
        setupAdapter()
        setupRecyclerView()
        setupFab()
        setupUiStateMonitoring()
    }

    private fun setupResultListeners() {
        setSelectingResourceTypeDialogResultListener()
        setInputURLDialogResultListener()
    }

    private fun setSelectingResourceTypeDialogResultListener() {
        childFragmentManager.setFragmentResultListener(
            SelectingResourceTypeDialog.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, result ->
            val resourceType = result.getString(SelectingResourceTypeDialog.KEY_RESOURCE_TYPE)
            if (resourceType == SelectingResourceTypeDialog.URL_RESOURCE)
                InputURLDialog().show(childFragmentManager, null)
            else
                Snackbar.make(view!!, "Will be added soon", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setInputURLDialogResultListener() {
        childFragmentManager.setFragmentResultListener(
            InputURLDialog.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, result ->
            val url = result.getString(InputURLDialog.KEY_URL)!!
            viewModel.searchAnime(url)
        }
    }

    private fun setupActionMode() {
        actionMode.listener = object : AnimeSearchActionMode.Listener {
            override fun onClickDeleteItem() {
                viewModel.deleteSearchedAnime(animeAdapter.selectedItems)
            }

            override fun onClickSelectAllItem() {
                animeAdapter.selectAll()
            }

            override fun onClickDeselectAllItem() {
                animeAdapter.deselectAll()
            }

            override fun onDestroy() {
                animeAdapter.disableSelectionMode()
            }
        }
    }

    private fun setupAdapter() {
        setActionListener()
        setSelectionModeListener()
        setDataObserver()
    }

    private fun setActionListener() {
        animeAdapter.actionListener = object : AnimeAdapter.ActionListener {
            override fun onAnimeClick(id: Long) {}

            override fun onClickFavoriteButton() {}
        }
    }

    private fun setSelectionModeListener() {
        animeAdapter.selectionModeListener = object : SelectableAdapter.SelectionModeListener {
            override fun onEnableMode(count: Int) {
                actionMode.start(binding.root, count)
            }

            override fun onToggleSelection(count: Int) {
                actionMode.setTitle(count)
            }

            override fun onAllSelected() {
                actionMode.hideSelectAllItem()
            }

            override fun onDisableMode() {
                actionMode.cancel()
            }
        }
    }

    private fun setDataObserver() {
        animeAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.recyclerView.scrollToPosition(0)
            }
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = animeAdapter
        }
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            SelectingResourceTypeDialog().show(childFragmentManager, null)
        }
    }

    private fun setupUiStateMonitoring() {
        setItemStateObserver()
        setUserMessageObserver()
    }

    private fun setItemStateObserver() {
        viewModel.itemUiState.observe(viewLifecycleOwner) { itemState ->
            animeAdapter.submitList(itemState)
        }
    }

    private fun setUserMessageObserver() {
        viewModel.userMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.userMessageShown()
            }
        }
    }
}