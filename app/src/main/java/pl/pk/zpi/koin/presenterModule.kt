package pl.pk.zpi.koin

import org.koin.dsl.module
import pl.pk.zpi.ui.login.LoginContract
import pl.pk.zpi.ui.login.LoginPresenter

val presenterModule = module {
    factory<LoginContract.Presenter> { LoginPresenter(get(), get()) }
}
