package com.android.chatgpt.data.datasource

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage as OpenAIChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.android.chatgpt.data.model.ChatMessage
import com.android.chatgpt.data.model.MessageRole
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenAIDataSource @Inject constructor(
    private val openAI: OpenAI
) {
    suspend fun getChatCompletion(messages: List<ChatMessage>): String {
        val openAIMessages = messages.map { it.toOpenAIChatMessage() }

        val completionRequest = ChatCompletionRequest(
            model = ModelId("gpt-4o-mini"),
            messages = openAIMessages
        )

        val completion = openAI.chatCompletion(completionRequest)
        return completion.choices.first().message.content ?: "No se pudo obtener una respuesta"
    }

    suspend fun generateConversationTitle(firstMessage: String): String {
        val systemMessage = OpenAIChatMessage(
            role = ChatRole.System,
            content = "Genera un título corto y descriptivo (máximo 6 palabras) para una conversación basado en el siguiente mensaje. No uses comillas ni puntuación."
        )

        val userMessage = OpenAIChatMessage(
            role = ChatRole.User,
            content = firstMessage
        )

        val completionRequest = ChatCompletionRequest(
            model = ModelId("gpt-4o-mini"),
            messages = listOf(systemMessage, userMessage)
        )

        val completion = openAI.chatCompletion(completionRequest)
        return completion.choices.first().message.content?.trim() ?: "Nueva conversación"
    }

    private fun ChatMessage.toOpenAIChatMessage(): OpenAIChatMessage {
        val role = when (this.role) {
            MessageRole.USER -> ChatRole.User
            MessageRole.ASSISTANT -> ChatRole.Assistant
            MessageRole.SYSTEM -> ChatRole.System
        }

        return OpenAIChatMessage(
            role = role,
            content = this.content
        )
    }
}