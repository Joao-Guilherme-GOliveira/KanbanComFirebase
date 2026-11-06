package com.joaog.task.util

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.database
import com.joaog.task.data.model.Task

object DatabaseManager {
	const val DB_NAME_USERS = "users"
	const val DB_NAME_TASKS = "task"

	val database get() = Firebase.database.reference

	fun getUser(id: String) = database.child(DB_NAME_USERS).child(id)

	fun createTask(userId: String, task: Task) =
		getUser(userId)
			.child(DB_NAME_TASKS)
			.child(task.id)
			.setValue(task.toMap())
	fun getTasksSnapshots(userId: String) =
		getUser(userId)
			.child(DB_NAME_TASKS)
			.get()
	fun snapshotToTasks(snapshots: DataSnapshot) =
		snapshots.children.mapNotNull { item -> item.getValue(Task::class.java) }
}