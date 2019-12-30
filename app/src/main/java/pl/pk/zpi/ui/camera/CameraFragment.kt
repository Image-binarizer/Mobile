package pl.pk.zpi.ui.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.camera.core.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_camera.*
import org.koin.android.ext.android.inject
import pl.pk.zpi.R
import pl.pk.zpi.ui.preview.PreviewFragment
import java.io.File

class CameraFragment : Fragment() {

    private val navigationController: NavController by lazy { findNavController() }
    private val previewConfig: PreviewConfig by inject()
    private val imageCaptureConfig: ImageCaptureConfig by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_camera, container, false)

    override fun onStart() {
        super.onStart()

        setWindowFlags()
        checkPermissions()

        galleryButton.setOnClickListener {
            navigationController.navigate(R.id.action_cameraFragment_to_galleryFragment)
        }
    }

    private fun checkPermissions() {
        if (isPermissionGranted()) {
            startPreview()
        } else {
            requestPermissions(CAMERA_PERMISSION, CAMERA_REQUEST_CODE)
        }
    }

    private fun setWindowFlags() {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
    }

    private fun startPreview() {
        texture.post { startCamera() }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                startPreview()
            } else {
                Toast.makeText(context, getString(R.string.missing_permission), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isPermissionGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED


    private fun startCamera() {
        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener {
            texture.surfaceTexture = it.surfaceTexture
        }

        val imageCapture = ImageCapture(imageCaptureConfig)
        shutterButton.setOnClickListener {
            val fileName = "${System.currentTimeMillis()}.jpg"
            imageCapture.takePicture(File(activity?.filesDir, fileName), onImageSaved(fileName))
        }

        CameraX.bindToLifecycle(this, preview, imageCapture)
    }

    private fun onImageSaved(fileName: String) = object : ImageCapture.OnImageSavedListener {
        override fun onError(imageCaptureError: ImageCapture.ImageCaptureError, message: String, cause: Throwable?) {
            Toast.makeText(context, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
        }

        override fun onImageSaved(file: File) {
            navigationController.navigate(
                R.id.action_cameraFragment_to_previewFragment,
                PreviewFragment.newBundle(fileName)
            )
        }
    }

    override fun onStop() {
        CameraX.unbindAll()
        super.onStop()
    }

    companion object {
        private val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
        private const val CAMERA_REQUEST_CODE = 1234
    }
}
