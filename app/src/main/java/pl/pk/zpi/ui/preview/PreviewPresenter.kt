package pl.pk.zpi.ui.preview

import android.util.Base64
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import pl.pk.zpi.domain.AuthTokenProvider
import pl.pk.zpi.koin.SchedulerProvider
import pl.pk.zpi.models.PhotoUploadRequest
import pl.pk.zpi.networking.Service
import java.io.File

class PreviewPresenter(
    private val schedulers: SchedulerProvider,
    private val service: Service,
    private val tokenProvider: AuthTokenProvider
) : PreviewContract.Presenter {

    private lateinit var view: PreviewContract.View
    private var filePath: String? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onViewPresent(view: PreviewContract.View, filePath: String) {
        this.view = view
        this.filePath = filePath

        view.displayPhoto(filePath)
    }

    override fun onSendTap() {
        filePath?.let {
            compositeDisposable += Single
                .fromCallable {
                    PhotoUploadRequest(
                        tokenProvider.read(),
                        File(it).name,
                        Base64.encodeToString(File(it).readBytes(), Base64.NO_WRAP)
                    )
                }
                .flatMapCompletable {
                    service.uploadPhoto(it)
                }
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.main())
                .doOnSubscribe {
                    view.showProgress()
                }
                .doAfterTerminate {
                    view.hideProgress()
                    File(it).delete()
                }
                .subscribeBy(
                    onComplete = {
                        view.goBack()
                    },
                    onError = {
                        view.showError()
                    }
                )
        }
    }

    override fun onCloseTap() {
        view.goBack()
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

}
