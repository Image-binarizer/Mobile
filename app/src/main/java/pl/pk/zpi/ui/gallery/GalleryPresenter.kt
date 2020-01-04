package pl.pk.zpi.ui.gallery

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import pl.pk.zpi.domain.AuthTokenProvider
import pl.pk.zpi.koin.SchedulerProvider
import pl.pk.zpi.models.GalleryRequest
import pl.pk.zpi.networking.Service

class GalleryPresenter(
    private val schedulerProvider: SchedulerProvider,
    private val service: Service,
    private val tokenProvider: AuthTokenProvider
): GalleryContract.Presenter {

    private lateinit var view: GalleryContract.View
    private val compositeDisposable = CompositeDisposable()

    override fun onViewPresent(view: GalleryContract.View) {
        this.view = view
        fetchImages()
    }

    private fun fetchImages() {
        val request = GalleryRequest(tokenProvider.read())
        compositeDisposable += service.getImages(request)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .subscribeBy(
                onSuccess = {
                    view.displayImages(it.body ?: emptyList())
                },
                onError = {
                    view.showError()
                }
            )

    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}
