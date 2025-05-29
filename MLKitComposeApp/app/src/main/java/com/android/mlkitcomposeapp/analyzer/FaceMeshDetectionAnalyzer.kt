package com.android.mlkitcomposeapp.analyzer

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.android.mlkitcomposeapp.ui.common.utils.adjustDimensionsForRotation
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.facemesh.FaceMesh
import com.google.mlkit.vision.facemesh.FaceMeshDetection

@SuppressLint("UnsafeOptInUsageError")
class FaceMeshDetectionAnalyzer(
    private val onFaceMeshDetected: (faces: MutableList<FaceMesh>, width: Int, height: Int) -> Unit
) : ImageAnalysis.Analyzer {

    private val meshDetector = FaceMeshDetection.getClient()

    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let { mediaImage ->
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            val image = InputImage.fromMediaImage(mediaImage, rotationDegrees)
            val (imageWidth, imageHeight) = adjustDimensionsForRotation(image.width, image.height, rotationDegrees)

            meshDetector.process(image)
                .addOnSuccessListener { meshes ->
                    onFaceMeshDetected(meshes, imageWidth, imageHeight)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } ?: imageProxy.close()
    }
}