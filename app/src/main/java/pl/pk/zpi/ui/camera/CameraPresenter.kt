package pl.pk.zpi.ui.camera

class CameraPresenter : CameraContract.Presenter {

    private lateinit var view: CameraContract.View

    override fun onViewPresent(view: CameraContract.View) {
        this.view = view
        view.initView()
    }

    override fun onShutterTap() {
        val fileName = "${System.currentTimeMillis()}.jpg"
        view.captureImage(fileName)
    }

    override fun onGalleryTap() {
        view.navigateToGallery()
    }
}
