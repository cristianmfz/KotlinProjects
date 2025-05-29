package com.android.chatgpt.ui.screen.chat

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.chatgpt.ui.screen.chat.components.ChatInputField
import com.android.chatgpt.ui.screen.chat.components.ChatMessageList
import com.android.chatgpt.ui.screen.chat.components.ConversationDrawer
import com.android.chatgpt.ui.screen.chat.components.EmptyChatPlaceholder
import com.android.chatgpt.ui.theme.ChatGPTTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var message by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ConversationDrawer(
                conversations = state.conversations,
                currentConversationId = state.currentConversationId,
                onConversationSelected = {
                    viewModel.selectConversation(it)
                    scope.launch {
                        drawerState.close()
                    }
                },
                onNewConversation = {
                    viewModel.createNewConversation()
                    scope.launch {
                        drawerState.close()
                    }
                },
                onDeleteConversation = { conversationId ->
                    viewModel.deleteConversation(conversationId)
                }
            )
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text("ChatGPT") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                ChatInputField(
                    message = message,
                    onMessageChange = { message = it },
                    onSendMessage = {
                        viewModel.sendMessage(message)
                        message = ""
                    },
                    isLoading = state.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .imePadding()
                )
            }
        ) { paddingValues ->
            if (state.messages.isEmpty()) {
                EmptyChatPlaceholder(
                    modifier = Modifier.padding(paddingValues)
                )
            } else {
                ChatMessageList(
                    messages = state.messages,
                    isLoading = state.isLoading,
                    contentPadding = paddingValues
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ChatGPTTheme {
        ChatScreen()
    }
}