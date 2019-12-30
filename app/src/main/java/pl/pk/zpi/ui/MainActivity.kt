package pl.pk.zpi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import pl.pk.zpi.R

class MainActivity : AppCompatActivity() {

    private val navigationController: NavController by lazy { nav_host_fragment.findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        when(navigationController.currentDestination?.id) {
            R.id.cameraFragment -> finish()
            else -> super.onBackPressed()
        }
    }
}
