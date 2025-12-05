package cl.MyMGroup.rentify.ui.camera

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class QrAnalyzer(
    private val onQrScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient()

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return

        val image = InputImage.fromMediaImage(
            mediaImage,
            imageProxy.imageInfo.rotationDegrees
        )

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    if (barcode.valueType == Barcode.TYPE_TEXT) {
                        barcode.rawValue?.let {
                            onQrScanned(it)
                        }
                    }
                }
            }
            .addOnFailureListener {
                // Error silencioso
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}
