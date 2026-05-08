package com.multiplatform.rickverseapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform