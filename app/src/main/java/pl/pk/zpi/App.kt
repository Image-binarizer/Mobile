package pl.pk.zpi

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import pl.pk.zpi.koin.androidModule
import pl.pk.zpi.koin.networkingModule
import pl.pk.zpi.koin.presenterModule
import pl.pk.zpi.koin.schedulerModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(networkingModule, presenterModule, schedulerModule, androidModule))
        }
    }

}
