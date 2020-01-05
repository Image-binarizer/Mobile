package pl.pk.zpi.ui.gallery

interface GalleryContract {
    interface View {
        fun displayImages(links: List<String>)
        fun showEmpty()
        fun showProgress()
        fun hideProgress()
        fun hideEmpty()
        fun showError()
    }
    interface Presenter {
        fun onViewPresent(view: View)
        fun unsubscribe()
        fun fetchImages()
    }
}