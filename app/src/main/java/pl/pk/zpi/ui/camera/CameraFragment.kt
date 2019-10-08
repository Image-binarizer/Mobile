package pl.pk.zpi.ui.camera

import android.graphics.Matrix
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Rational
import android.util.Size
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_camera.*
import pl.pk.zpi.R
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import java.io.File

class CameraFragment : Fragment() {

    private val navigationController: NavController by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_camera, container, false)

    override fun onStart() {
        super.onStart()

        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        texture.post { startCamera() }

        galleryButton.setOnClickListener {
            navigationController.navigate(R.id.action_cameraFragment_to_galleryFragment)
        }
    }

    private fun startCamera() {
        val metrics = DisplayMetrics().also { texture.display.getRealMetrics(it) }
        val screenSize = Size(metrics.widthPixels, metrics.heightPixels)
        val screenAspectRatio = Rational(metrics.widthPixels, metrics.heightPixels)

        val previewConfig = PreviewConfig.Builder().apply {
            setLensFacing(CameraX.LensFacing.BACK)
            setTargetResolution(screenSize)
            setTargetAspectRatio(screenAspectRatio)
            setTargetRotation(texture.display.rotation)
        }.build()

        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener {
            texture.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                setLensFacing(CameraX.LensFacing.BACK)
                setTargetAspectRatio(screenAspectRatio)
                setTargetRotation(texture.display.rotation)
                setCaptureMode(ImageCapture.CaptureMode.MAX_QUALITY)
            }.build()

        val imageCapture = ImageCapture(imageCaptureConfig)
        shutterButton.setOnClickListener {
            imageCapture.takePicture(File(activity?.filesDir, "photo.jpg"),
                object : ImageCapture.OnImageSavedListener {
                    override fun onError(
                        imageCaptureError: ImageCapture.ImageCaptureError,
                        message: String,
                        cause: Throwable?
                    ) {
                        Toast.makeText(context, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
                    }

                    override fun onImageSaved(file: File) {
                        navigationController.navigate(R.id.action_cameraFragment_to_previewFragment)
                    }
                })
        }

        CameraX.bindToLifecycle(this, preview, imageCapture)
    }

    private fun updateTransform() {
        val matrix = Matrix()
        val centerX = texture.width / 2f
        val centerY = texture.height / 2f

        val rotationDegrees = when (texture.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)
        texture.setTransform(matrix)
    }

    override fun onStop() {
        CameraX.unbindAll()
        super.onStop()
    }

}
