package com.joaog.task.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.joaog.task.R
import com.joaog.task.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
	private var _binding: FragmentSplashBinding? = null
	private val binding get() = _binding!!

	private lateinit var auth: FirebaseAuth

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSplashBinding.inflate(inflater, container, false)
		auth = Firebase.auth

		return binding.root
	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		Handler(Looper.getMainLooper()).postDelayed(
			{ checkAuth() },
			3000
		)
	}


	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}


	private fun checkAuth() {
		try {
			if (auth.currentUser != null) {
				findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
				return
			}
		} catch (e: Exception) {
			Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
		}

		findNavController().navigate(R.id.action_splashFragment_to_authentication)
	}
}