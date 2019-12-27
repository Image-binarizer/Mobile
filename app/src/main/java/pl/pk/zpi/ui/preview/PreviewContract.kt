package pl.pk.zpi.ui.preview

interface PreviewContract {
    interface View {
        fun displayPhoto(fileName: String)
        fun goBack()
        fun showError()
        fun showProgress()
        fun hideProgress()
    }
    interface Presenter {
        fun onViewPresent(view: View, filePath: String)
        fun onSendTap()
        fun onCloseTap()
        fun unsubscribe()
    }
}