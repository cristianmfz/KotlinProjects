package com.multiplatform.kmpbooks

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform