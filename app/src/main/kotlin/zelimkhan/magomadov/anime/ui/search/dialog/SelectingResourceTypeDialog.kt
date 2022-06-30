package zelimkhan.magomadov.anime.ui.search.dialog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import zelimkhan.magomadov.anime.R
import zelimkhan.magomadov.anime.databinding.DialogSelectingResourceTypeBinding
import zelimkhan.magomadov.anime.ui.core.dialog.BaseDialogFragment

class SelectingResourceTypeDialog : BaseDialogFragment(R.layout.dialog_selecting_resource_type) {
    private lateinit var binding: DialogSelectingResourceTypeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogSelectingResourceTypeBinding.bind(view)

        binding.urlResource.setOnClickListener {
            setFragmentResult(REQUEST_KEY, bundleOf(KEY_RESOURCE_TYPE to URL_RESOURCE))
            dismiss()
        }

        binding.localResources.setOnClickListener {
            setFragmentResult(REQUEST_KEY, bundleOf(KEY_RESOURCE_TYPE to LOCAL_RESOURCE))
            dismiss()
        }
    }

    companion object {
        val REQUEST_KEY: String = SelectingResourceTypeDialog::class.java.name
        const val KEY_RESOURCE_TYPE = "KEY_RESOURCE_TYPE"
        const val URL_RESOURCE = "URL"
        const val LOCAL_RESOURCE = "LOCAL_RESOURCES"
    }
}