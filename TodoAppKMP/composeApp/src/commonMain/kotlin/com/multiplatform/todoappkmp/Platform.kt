package com.multiplatform.todoappkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform