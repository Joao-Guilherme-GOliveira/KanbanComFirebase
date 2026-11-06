package com.joaog.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.joaog.task.R
import com.joaog.task.databinding.FragmentLoginBinding
import com.joaog.task.util.showBottomSheet

class LoginFragment : Fragment() {

	private var _binding: FragmentLoginBinding? = null
	private val binding get() = _binding!!

	private lateinit var auth: FirebaseAuth

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentLoginBinding.inflate(inflater, container, false)
		auth = Firebase.auth

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.buttonLogin.setOnClickListener {
			validateAndLogin()
		}
		binding.buttonRegister.setOnClickListener {
			findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
		}
		binding.buttonRecover.setOnClickListener {
			findNavController().navigate(R.id.action_loginFragment_to_recoverAccountFragment)
		}
	}

	private fun validateAndLogin() {
		val email = binding.inputEmail.text.toString()
		val password = binding.inputSenha.text.toString()

		if (email.isBlank()) {
			showBottomSheet(message = getString(R.string.email_empty))
			return
		}
		if (password.isBlank()) {
			showBottomSheet(message = getString(R.string.password_empty))
			return
		}

		loginUser(email, password)
	}

	private fun loginUser(email: String, password: String) {
		binding.progressBar.isVisible = true

		auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
			try {
				val exception = task.exception
				if (!task.isSuccessful && exception != null) {
					throw Exception(exception.message)
				}

				findNavController().navigate(R.id.action_global_homeFragment)
			} catch (e: Exception) {
				Toast.makeText(
					requireContext(),
					e.message.toString(),
					Toast.LENGTH_SHORT,
				).show()
			} finally {
				binding.progressBar.isVisible = false
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}