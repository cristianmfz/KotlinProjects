package com.multiplatform.locationtracking

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform