package com.android.back4appchat.domain

import com.parse.ParseUser

data class ChatRoom(
    val objectId: String,
    val participants: List<ParseUser>,
    val messages: List<Message>,
    val lastSeenMessages: Map<String, String>
)