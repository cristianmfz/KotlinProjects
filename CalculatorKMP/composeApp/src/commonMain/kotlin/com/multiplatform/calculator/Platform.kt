package com.multiplatform.calculator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform