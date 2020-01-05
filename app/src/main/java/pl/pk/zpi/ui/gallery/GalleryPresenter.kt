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

    override fun fetchImages() {
        view.displayImages(emptyList())

        val request = GalleryRequest(tokenProvider.read())
        compositeDisposable += service.getImages(request)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .doOnSubscribe {
                view.hideEmpty()
                view.showProgress()
            }
            .doAfterTerminate {
                view.hideProgress()
            }
            .subscribeBy(
                onSuccess = {
                    if (it.body.isEmpty()) {
                        view.showEmpty()
                    } else {
                        view.displayImages(it.body)
                    }
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
