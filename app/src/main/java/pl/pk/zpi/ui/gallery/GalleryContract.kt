package pl.pk.zpi.ui.gallery

interface GalleryContract {
    interface View {
        fun displayImages()
    }
    interface Presenter {
        fun onViewPresent(view: View)
        fun unsubscribe()
    }
}