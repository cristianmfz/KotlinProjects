package com.multiplatform.kmpmovies

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform