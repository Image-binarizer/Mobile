package pl.pk.zpi.ui.login

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import pl.pk.zpi.koin.SchedulerProvider
import pl.pk.zpi.models.RegisterRequest
import pl.pk.zpi.networking.Service

class LoginPresenter(
    private val schedulers: SchedulerProvider,
    private val service: Service
) : LoginContract.Presenter {

    private lateinit var view: LoginContract.View
    private var screenMode = ScreenMode.LOGIN
    private val compositeDisposable = CompositeDisposable()

    override fun onViewPresent(view: LoginContract.View) {
        this.view = view
        view.initViews()
    }

    override fun onLoginTap(email: String, password: String) {
        when (screenMode) {
            ScreenMode.LOGIN -> login(email, password)
            ScreenMode.REGISTER -> register(email, password)
        }
    }

    private fun register(email: String, password: String) {
        compositeDisposable += service.register(RegisterRequest(email, password))
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .doOnSubscribe { view.showProgress() }
            .doAfterTerminate { view.hideProgress() }
            .subscribeBy(
                onComplete = { view.goToCamera() },
                onError = { view.showError() }
            )
    }

    private fun login(email: String, password: String) {
        view.goToCamera()
    }

    override fun onChangeModeTap() {
        when (screenMode) {
            ScreenMode.LOGIN -> {
                screenMode = ScreenMode.REGISTER
                view.switchToRegister()
            }
            ScreenMode.REGISTER -> {
                screenMode = ScreenMode.LOGIN
                view.switchToLogin()
            }
        }
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

}
