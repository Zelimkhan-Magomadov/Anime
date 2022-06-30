package zelimkhan.magomadov.anime.ui.search.dialog

import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import zelimkhan.magomadov.anime.R
import zelimkhan.magomadov.anime.databinding.DialogInputUrlBinding
import zelimkhan.magomadov.anime.ui.core.dialog.BaseDialogFragment
import zelimkhan.magomadov.anime.ui.core.extension.isValidURL

class InputURLDialog : BaseDialogFragment(R.layout.dialog_input_url) {
    private lateinit var binding: DialogInputUrlBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogInputUrlBinding.bind(view)

        binding.urlInput.apply {
            doAfterTextChanged { binding.search.isEnabled = it.isValidURL }
            pastClipboardText(this)
        }

        binding.search.setOnClickListener {
            val url = binding.urlInput.text.toString()
            setFragmentResult(REQUEST_KEY, bundleOf(KEY_URL to url))
            dismiss()
        }

        binding.urlInputLayout.setEndIconOnClickListener {
            pastClipboardText(binding.urlInput)
        }
    }

    private fun pastClipboardText(editText: EditText) {
        editText.apply {
            editText.setText(getClipboardText())
            editText.setSelection(length())
        }
    }

    private fun getClipboardText(): CharSequence {
        val clipboard = getSystemService(requireContext(), ClipboardManager::class.java)!!
        return clipboard.primaryClip?.getItemAt(0)?.text ?: ""
    }

    companion object {
        val REQUEST_KEY: String = InputURLDialog::class.java.name
        const val KEY_URL = "KEY_URL"
    }
}