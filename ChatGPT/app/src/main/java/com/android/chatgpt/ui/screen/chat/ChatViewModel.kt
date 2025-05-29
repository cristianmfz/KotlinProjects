package com.android.chatgpt.ui.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.android.chatgpt.data.model.ChatMessage
import com.android.chatgpt.data.repository.ChatRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ConversationItem(
    val id: String,
    val title: String
)

data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val conversations: List<ConversationItem> = emptyList(),
    val currentConversationId: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    private val _currentConversationId = MutableStateFlow<String?>(null)

    private val messagesFlow = _currentConversationId.flatMapLatest { conversationId ->
        if (conversationId != null) {
            chatRepository.getMessagesForConversation(conversationId)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val conversationsFlow = chatRepository.getAllConversations().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val state: StateFlow<ChatState> = combine(
        messagesFlow,
        conversationsFlow,
        _currentConversationId,
        _uiState
    ) { messages, conversations, currentId, uiState ->
        ChatState(
            messages = messages,
            conversations = conversations.map {
                ConversationItem(id = it.id, title = it.title)
            },
            currentConversationId = currentId,
            isLoading = uiState.isLoading,
            error = uiState.error
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ChatState()
    )

    init {
        viewModelScope.launch {
            _currentConversationId.value = chatRepository.getCurrentConversationId()
        }
    }

    fun sendMessage(content: String) {
        if (content.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            chatRepository.sendMessage(content)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false) }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Error: ${exception.localizedMessage}"
                        )
                    }
                }
        }
    }

    fun createNewConversation() {
        viewModelScope.launch {
            val newConversationId = chatRepository.createNewConversation("Nueva conversación")
            _currentConversationId.value = newConversationId
        }
    }

    fun selectConversation(conversationId: String) {
        _currentConversationId.value = conversationId
    }

    fun deleteCurrentConversation() {
        viewModelScope.launch {
            _currentConversationId.value?.let { id ->
                chatRepository.deleteConversation(id)
                _currentConversationId.value = chatRepository.getCurrentConversationId()
            }
        }
    }

    fun deleteConversation(conversationId: String) {
        viewModelScope.launch {
            val wasCurrentConversation = _currentConversationId.value == conversationId

            chatRepository.deleteConversation(conversationId)

            if (wasCurrentConversation) {
                _currentConversationId.value = chatRepository.getCurrentConversationId()
            }
        }
    }

    private data class UiState(
        val isLoading: Boolean = false,
        val error: String? = null
    )
}