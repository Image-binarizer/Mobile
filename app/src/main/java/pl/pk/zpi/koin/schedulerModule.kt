package pl.pk.zpi.koin

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module

val schedulerModule = module {
    single<SchedulerProvider> {
        object : SchedulerProvider {
            override fun io(): Scheduler = Schedulers.io()
            override fun main(): Scheduler = AndroidSchedulers.mainThread()
        }
    }
}

interface SchedulerProvider {
    fun io(): Scheduler
    fun main(): Scheduler
}
