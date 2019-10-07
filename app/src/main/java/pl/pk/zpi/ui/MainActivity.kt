package pl.pk.zpi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.pk.zpi.ui.login.LoginFragment
import pl.pk.zpi.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHost, LoginFragment())
            .commit()
    }
}
