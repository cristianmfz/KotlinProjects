package com.multiplatform.kmpbooks.ui.screen.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multiplatform.kmpbooks.data.BookDatabase
import com.multiplatform.kmpbooks.domain.Book
import com.multiplatform.kmpbooks.navigation.BOOK_ID
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailViewModel(
    private val database: BookDatabase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var selectedBook: MutableState<Book?> = mutableStateOf(null)
        private set
    var isFavorite = mutableStateOf(false)
        private set
    private val selectedBookId = savedStateHandle.get<Int>(BOOK_ID) ?: 0

    init {
        viewModelScope.launch {
            database.bookDao()
                .getBookByIdFlow(selectedBookId)
                .collectLatest {
                    selectedBook.value = it
                    isFavorite.value = it?.isFavorite ?: false
                }
        }
    }

    fun setFavoriteBook() {
        viewModelScope.launch {
            if(selectedBook.value?._id != null) {
                database.bookDao()
                    .setFavoriteBook(
                        bookId = selectedBook.value!!._id,
                        isFavorite = !isFavorite.value
                    )
            }
        }
    }

    fun deleteBook() {
        viewModelScope.launch {
            database.bookDao()
                .deleteBookById(selectedBookId)
        }
    }
}