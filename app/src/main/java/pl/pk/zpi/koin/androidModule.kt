package pl.pk.zpi.koin

import android.content.SharedPreferences
import android.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pl.pk.zpi.domain.AuthTokenProvider

val androidModule = module {
    single<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single { AuthTokenProvider(get()) }
}
