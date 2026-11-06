package com.joaog.task.util

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.joaog.task.R
import com.joaog.task.databinding.BottomSheetBinding

fun Fragment.initToolbar(toolbar: Toolbar) {
	val superActivity = activity as AppCompatActivity
	superActivity.setSupportActionBar(toolbar)
	superActivity.title = ""
	superActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

	toolbar.setNavigationOnClickListener {
		activity?.onBackPressedDispatcher?.onBackPressed()
	}
}

fun Fragment.showBottomSheet(
	titleDialog: Int? = null,
	titleButton: Int? = null,
	message: String,
	onClick: () -> Unit = {}
) {
	val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
	val binding = BottomSheetBinding.inflate(layoutInflater, null, false)

	binding.title.text = getText(titleDialog ?: R.string.bottom_sheet_warning)
	binding.textMessage.text = message
	binding.buttonOk.text = getText(titleButton ?: R.string.bottom_sheet_ok)
	binding.buttonOk.setOnClickListener {
		onClick()
		bottomSheetDialog.dismiss()
	}

	bottomSheetDialog.setContentView(binding.root)
	bottomSheetDialog.show()
}

