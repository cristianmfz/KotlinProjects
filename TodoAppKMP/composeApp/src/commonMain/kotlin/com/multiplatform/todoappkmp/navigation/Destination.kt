package com.multiplatform.todoappkmp.navigation

import kotlinx.serialization.Serializable

const val TASK_ID = "taskId"

sealed class Destination {
    @Serializable
    data object Home: Destination()

    @Serializable
    data class Task(val taskId: String?): Destination()
}