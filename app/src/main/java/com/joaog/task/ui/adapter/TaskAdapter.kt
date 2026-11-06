package com.joaog.task.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joaog.task.R
import com.joaog.task.data.model.Status
import com.joaog.task.data.model.Task
import com.joaog.task.databinding.ItemTaskBinding

class TaskAdapter(
	private val context: Context,
	private val taskSelected: (Task, Int) -> Unit
): ListAdapter<Task, TaskAdapter.MyViewHolder>(DIFF_CALLBACK) {
	companion object {
		const val SELECT_BACK: Int = 1
		const val SELECT_REMOVER: Int = 2
		const val SELECT_EDIT: Int = 3
		const val SELECT_DETAILS: Int = 4
		const val SELECT_NEXT: Int = 5

		private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
			override fun areItemsTheSame(oldItem: Task, newItem: Task) =
				oldItem.id == newItem.id && oldItem.description == newItem.description

			override fun areContentsTheSame(oldItem: Task, newItem: Task) =
				oldItem == newItem && oldItem.description == newItem.description
		}
	}


	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): MyViewHolder {
		val view = ItemTaskBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)
		return MyViewHolder(view)
	}

	override fun onBindViewHolder(
		holder: MyViewHolder,
		position: Int
	) {
		val task = getItem(position)
		holder.binding.textDescription.text = task.description

		setIndicators(task, holder)
	}

	private fun setIndicators(task: Task, holder: MyViewHolder) {
		when (task.status) {
			Status.TODO -> {
				holder.binding.buttonBack.isVisible = false
				holder.binding.buttonForward.setOnClickListener { taskSelected(task, SELECT_NEXT)}
			}
			Status.DOING -> {
				holder.binding.buttonBack.setColorFilter(ContextCompat.getColor(context, R.color.color_status_todo))
				holder.binding.buttonForward.setColorFilter(ContextCompat.getColor(context, R.color.color_status_done))
				holder.binding.buttonForward.setOnClickListener { taskSelected(task, SELECT_NEXT) }
				holder.binding.buttonBack.setOnClickListener { taskSelected(task, SELECT_BACK) }
			}
			Status.DONE -> {
				holder.binding.buttonForward.isVisible = false
				holder.binding.buttonBack.setOnClickListener { taskSelected(task, SELECT_BACK) }
			}
		}

		holder.binding.buttonDelete.setOnClickListener { taskSelected(task, SELECT_REMOVER) }
		holder.binding.buttonEdit.setOnClickListener { taskSelected(task, SELECT_EDIT) }
		holder.binding.buttonDetails.setOnClickListener { taskSelected(task, SELECT_DETAILS) }
	}


	class MyViewHolder(val binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root)
}