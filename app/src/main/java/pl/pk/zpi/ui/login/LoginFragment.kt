package pl.pk.zpi.ui.login

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.*
import pl.pk.zpi.R

class LoginFragment : Fragment() {

    private var isLoginMode = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_login, container, false)

    override fun onStart() {
        super.onStart()


        val scale = resources.displayMetrics.density
        val distance = card.cameraDistance * (scale * 1.33).toFloat()
        card.cameraDistance = distance

        buttonChangeMode.setOnClickListener {
            if (isLoginMode) {
                switchMode(getString(R.string.register), getString(R.string.already_have_account), 1)
            } else {
                switchMode(getString(R.string.login), getString(R.string.don_t_have_an_account_register), -1)
            }
            isLoginMode = !isLoginMode
        }
    }

    private fun switchMode(loginButtonText: String, switchModeText: String, directionFactor: Int) {
        card.animate()
            .withLayer()
            .rotationY(-90.0f * directionFactor)
            .setDuration(300)
            .withStartAction { card.elevation = 0.0f }
            .withEndAction {
                buttonLogin.text = loginButtonText
                buttonChangeMode.text = switchModeText

                card.rotationY = 90.0f * directionFactor
                card.animate()
                    .withLayer()
                    .rotationY(0.0f)
                    .setDuration(300)
                    .withEndAction {
                        card.elevation =
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.0f, resources.displayMetrics)
                    }
                    .start()
            }
            .start()
    }

}
