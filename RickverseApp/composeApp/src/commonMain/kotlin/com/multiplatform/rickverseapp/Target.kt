package com.multiplatform.rickverseapp

expect fun getCurrentTarget(): Target

enum class Target {
    Ios, Android, Desktop
}

fun isIos() = getCurrentTarget() == Target.Ios
fun isAndroid() = getCurrentTarget() == Target.Android
fun isDesktop() = getCurrentTarget() == Target.Desktop