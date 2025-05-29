package com.android.back4appchat.util

import com.parse.ParseObject
import com.parse.ParseUser
import com.android.back4appchat.domain.Message

fun mapMessages(chatRoom: ParseObject): List<Message> {
    val messageObjects = chatRoom.getList<ParseObject>(
        ChatRoomTable.MESSAGES
    ) ?: emptyList()
    return messageObjects.map { parseObject ->
        Message(
            objectId = parseObject.objectId,
            chatRoom = chatRoom,
            owner = parseObject.getParseUser(MessageTable.OWNER)
                ?: ParseUser.getCurrentUser(),
            text = parseObject.getString(MessageTable.TEXT) ?: "",
            timestamp = parseObject.getLong(MessageTable.TIMESTAMP)
        )
    }
}