package com.joaog.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.joaog.task.R
import com.joaog.task.databinding.FragmentRecoverAccountBinding
import com.joaog.task.util.initToolbar
import com.joaog.task.util.showBottomSheet

class RecoverAccountFragment : Fragment() {

	private var _binding: FragmentRecoverAccountBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initToolbar(binding.toolbar)

		binding.buttonRecover.setOnClickListener {
			if (areInputsValid()) {
				Toast.makeText(requireContext(), "Compila OK", Toast.LENGTH_SHORT).show()
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}


	private fun areInputsValid(): Boolean {
		val email = binding.inputEmail.text.toString().trim()

		if (email.isBlank()) {
			showBottomSheet(message = getString(R.string.recover_email_empty))
			return false
		}

		return true
	}
}