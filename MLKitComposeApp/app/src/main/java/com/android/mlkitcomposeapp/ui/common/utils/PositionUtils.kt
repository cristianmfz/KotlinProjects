package com.android.mlkitcomposeapp.ui.common.utils

import android.graphics.PointF
import androidx.compose.ui.geometry.Size

fun adjustPoint(point: PointF, imageWidth: Int, imageHeight: Int, screenWidth: Int, screenHeight: Int): PointF {
    val x = point.x / imageWidth * screenWidth
    val y = point.y / imageHeight * screenHeight

    return PointF(x, y)
}

fun adjustSize(size: Size, imageWidth: Int, imageHeight: Int, screenWidth: Int, screenHeight: Int): Size {
    val width = size.width / imageWidth * screenWidth
    val height = size.height / imageHeight * screenHeight

    return Size(width, height)
}

fun adjustDimensionsForRotation(imageWidth: Int, imageHeight: Int, rotationDegrees: Int): Pair<Int, Int> {
    return if (rotationDegrees == 90 || rotationDegrees == 270) {
        imageHeight to imageWidth // swap
    } else {
        imageWidth to imageHeight // keep
    }
}