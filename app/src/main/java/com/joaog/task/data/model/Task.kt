package com.joaog.task.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task (
	val id: String,
	val description: String,
	val status: Status = Status.TODO
): Parcelable {
	// Necessário pro firebase funcionar
	constructor() : this("", "", Status.TODO)

	fun toMap() = mapOf(
		"description" to description,
		"status" to status.toString().uppercase()
	)
}