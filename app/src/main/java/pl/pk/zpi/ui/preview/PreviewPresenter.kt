package pl.pk.zpi.ui.preview

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.pk.zpi.domain.AuthTokenProvider
import pl.pk.zpi.koin.SchedulerProvider
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
            val file = File(it)
            val request =
                MultipartBody.Part.createFormData(FILE_FIELD, file.name, RequestBody.create(IMAGE_MIME, file))
            service.uploadPhoto(request, tokenProvider.read())
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.main())
                .doOnSubscribe { view.showProgress() }
                .doAfterTerminate { view.hideProgress() }
                .subscribeBy (
                    onComplete = {
                        view.goBack()
                    },
                    onError = {
                        view.showError()
                    }
                )
        }

        view.goBack()
    }

    override fun onCloseTap() {
        view.goBack()
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    companion object {
        private const val FILE_FIELD = "file"
        private val IMAGE_MIME = MediaType.parse("image/jpeg")
    }
}
