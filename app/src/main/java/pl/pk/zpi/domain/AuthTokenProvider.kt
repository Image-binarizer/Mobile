package pl.pk.zpi.domain

import android.content.SharedPreferences
import androidx.core.content.edit

class AuthTokenProvider(private val sharedPrefs: SharedPreferences) {

    fun save(token: String) {
        sharedPrefs.edit {
            putString(AUTH_TOKEN_KEY, token)
        }
    }

    fun read() = sharedPrefs.getString(AUTH_TOKEN_KEY, null)


    companion object {
        private const val AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY"
    }
}
