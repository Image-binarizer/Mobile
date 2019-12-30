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

class CameraFragment : Fragment(), CameraContract.View {

    private val navigationController: NavController by lazy { findNavController() }
    private val presenter: CameraContract.Presenter by inject()
    private val previewConfig: PreviewConfig by inject()
    private val imageCaptureConfig: ImageCaptureConfig by inject()
    private lateinit var preview: Preview
    private lateinit var imageCapture: ImageCapture

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_camera, container, false)

    override fun onStart() {
        super.onStart()
        presenter.onViewPresent(this)
    }

    override fun initView() {
        setWindowFlags()
        checkPermissions()

        galleryButton.setOnClickListener { presenter.onGalleryTap() }
        shutterButton.setOnClickListener { presenter.onShutterTap() }
        missingPermission.setOnClickListener { checkPermissions() }
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                startPreview()
                hideMissingPermission()
            } else {
                showMissingPermission()
            }
        }
    }

    private fun showMissingPermission() {
        missingPermission.visibility = View.VISIBLE
    }

    private fun hideMissingPermission() {
        missingPermission.visibility = View.GONE
    }

    private fun isPermissionGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    private fun startPreview() {
        texture.post {
            this.preview = Preview(previewConfig)
            this.imageCapture = ImageCapture(imageCaptureConfig)

            preview.setOnPreviewOutputUpdateListener {
                texture.surfaceTexture = it.surfaceTexture
            }

            CameraX.bindToLifecycle(this, preview, imageCapture)
        }
    }

    override fun captureImage(fileName: String) {
        imageCapture.takePicture(File(activity?.filesDir, fileName), onImageSaved())
    }

    private fun onImageSaved() = object : ImageCapture.OnImageSavedListener {
        override fun onError(imageCaptureError: ImageCapture.ImageCaptureError, message: String, cause: Throwable?) {
            Toast.makeText(context, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
        }

        override fun onImageSaved(file: File) {
            navigateToPreview(file.name)
        }
    }

    override fun navigateToGallery() {
        navigationController.navigate(R.id.action_cameraFragment_to_galleryFragment)
    }

    override fun navigateToPreview(fileName: String) {
        navigationController.navigate(
            R.id.action_cameraFragment_to_previewFragment,
            PreviewFragment.newBundle(fileName)
        )
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
