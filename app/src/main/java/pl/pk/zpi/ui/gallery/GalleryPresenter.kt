package pl.pk.zpi.ui.gallery

import io.reactivex.disposables.CompositeDisposable
import pl.pk.zpi.koin.SchedulerProvider
import pl.pk.zpi.networking.Service

class GalleryPresenter(
    private val schedulerProvider: SchedulerProvider,
    private val service: Service
): GalleryContract.Presenter {

    private lateinit var view: GalleryContract.View
    private val compositeDisposable = CompositeDisposable()

    override fun onViewPresent(view: GalleryContract.View) {
        this.view = view
        view.displayImages()
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}
