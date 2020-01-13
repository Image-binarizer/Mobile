package pl.pk.zpi.koin

import okhttp3.OkHttpClient
import org.koin.dsl.module
import pl.pk.zpi.networking.BASE_URL
import pl.pk.zpi.networking.Service
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkingModule = module {

    single {
        OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single<Service> { get<Retrofit>().create(Service::class.java) }

}
