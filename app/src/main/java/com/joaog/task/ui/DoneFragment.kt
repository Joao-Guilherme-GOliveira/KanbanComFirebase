package com.joaog.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.joaog.task.data.model.Status
import com.joaog.task.data.model.Task
import com.joaog.task.databinding.FragmentDoneBinding
import com.joaog.task.ui.adapter.TaskAdapter
import com.joaog.task.util.DatabaseManager


class DoneFragment : Fragment() {
	private var _binding: FragmentDoneBinding? = null
	private val binding get() = _binding!!

	private lateinit var auth: FirebaseAuth
	private lateinit var taskAdapter: TaskAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentDoneBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		auth = Firebase.auth

		initRecyclerViewTask()
		getTask()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}


	private fun getTask() {
		DatabaseManager
			.getTasksSnapshots(auth.currentUser?.uid ?: "")
			.addOnSuccessListener { snapshot ->
				if (!snapshot.exists()) return@addOnSuccessListener

				val tasks = DatabaseManager.snapshotToTasks(snapshot).filter {
						task -> task.status == Status.DONE
				}

				taskAdapter.submitList(tasks)
			}
	}


	private fun initRecyclerViewTask() {
		taskAdapter = TaskAdapter(requireContext()) { task, option -> optionSelected(task, option)}
		with(binding.recyclerViewTask) {
			layoutManager = LinearLayoutManager(requireContext())
			setHasFixedSize(true)
			adapter = taskAdapter
		}
	}

	private fun optionSelected(task: Task, option: Int) {
		val message = when (option) {
			TaskAdapter.SELECT_REMOVER -> "Removendo ${task.description}"
			TaskAdapter.SELECT_EDIT -> "Editando ${task.description}"
			TaskAdapter.SELECT_DETAILS -> "Detalhes ${task.description}"
			TaskAdapter.SELECT_NEXT -> "Próximo"
			TaskAdapter.SELECT_BACK -> "Anterior"
			else -> ""
		}
		Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
	}
}