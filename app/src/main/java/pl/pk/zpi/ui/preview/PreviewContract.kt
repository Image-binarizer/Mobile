package pl.pk.zpi.ui.preview

interface PreviewContract {
    interface View {
        fun displayPhoto(fileName: String?)
        fun goBack()
    }
    interface Presenter {
        fun onViewPresent(view: View, fileName: String?)
        fun onSendTap()
        fun onCloseTap()
        fun unsubscribe()
    }
}