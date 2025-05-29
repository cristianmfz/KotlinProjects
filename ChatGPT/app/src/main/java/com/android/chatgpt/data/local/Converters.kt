package com.android.chatgpt.data.local

import androidx.room.TypeConverter
import com.android.chatgpt.data.model.MessageRole

class Converters {
    @TypeConverter
    fun fromMessageRole(role: MessageRole): String {
        return role.name
    }

    @TypeConverter
    fun toMessageRole(roleName: String): MessageRole {
        return MessageRole.valueOf(roleName)
    }
}