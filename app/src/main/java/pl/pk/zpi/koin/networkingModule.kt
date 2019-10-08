package pl.pk.zpi.koin

import org.koin.dsl.module
import pl.pk.zpi.networking.BASE_URL
import pl.pk.zpi.networking.Service
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkingModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single<Service> { get<Retrofit>().create(Service::class.java) }

}
