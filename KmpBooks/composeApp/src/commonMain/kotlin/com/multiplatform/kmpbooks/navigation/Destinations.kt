package com.multiplatform.kmpbooks.navigation

import kotlinx.serialization.Serializable

const val BOOK_ID = "bookId"

@Serializable
object Home

@Serializable
data class Detail(val bookId: Int)

@Serializable
data class Manage(val bookId: Int = -1)