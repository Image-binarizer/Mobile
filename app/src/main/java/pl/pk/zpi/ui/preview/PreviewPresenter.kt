package pl.pk.zpi.ui.preview

import io.reactivex.disposables.CompositeDisposable
import pl.pk.zpi.koin.SchedulerProvider
import pl.pk.zpi.networking.Service

class PreviewPresenter(
    private val schedulerProvider: SchedulerProvider,
    private val service: Service
) : PreviewContract.Presenter {

    private lateinit var view: PreviewContract.View
    private var fileName: String? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onViewPresent(view: PreviewContract.View, fileName: String?) {
        this.view = view
        this.fileName = fileName

        view.displayPhoto(fileName)
    }

    override fun onSendTap() {
        // TODO Send photo

        view.goBack()
    }

    override fun onCloseTap() {
        view.goBack()
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}
