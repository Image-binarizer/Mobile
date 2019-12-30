package pl.pk.zpi.ui.camera

interface CameraContract {
    interface View {
        fun navigateToGallery()
        fun navigateToPreview(fileName: String)
        fun captureImage(fileName: String)
        fun initView()
    }
    interface Presenter {
        fun onViewPresent(view: View)
        fun onShutterTap()
        fun onGalleryTap()
    }
}
