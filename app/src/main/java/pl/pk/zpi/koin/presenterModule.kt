package pl.pk.zpi.koin

import org.koin.dsl.module
import pl.pk.zpi.ui.camera.CameraContract
import pl.pk.zpi.ui.camera.CameraPresenter
import pl.pk.zpi.ui.gallery.GalleryContract
import pl.pk.zpi.ui.gallery.GalleryPresenter
import pl.pk.zpi.ui.login.LoginContract
import pl.pk.zpi.ui.login.LoginPresenter
import pl.pk.zpi.ui.preview.PreviewContract
import pl.pk.zpi.ui.preview.PreviewPresenter

val presenterModule = module {
    factory<LoginContract.Presenter> { LoginPresenter(get(), get(), get()) }
    factory<PreviewContract.Presenter> { PreviewPresenter(get(), get(), get()) }
    factory<GalleryContract.Presenter> { GalleryPresenter(get(), get()) }
    factory<CameraContract.Presenter> { CameraPresenter() }
}
