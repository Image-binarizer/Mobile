package pl.pk.zpi.ui.login

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import pl.pk.zpi.R

class LoginFragment : Fragment(), LoginContract.View {

    private val presenter: LoginContract.Presenter by inject()
    private val snackbar: Snackbar by lazy {
        Snackbar.make(fragment, getString(R.string.please_wait), Snackbar.LENGTH_INDEFINITE)
    }
    private val navigationController: NavController by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_login, container, false)

    override fun onStart() {
        super.onStart()
        presenter.onViewPresent(this)
    }

    override fun initViews() {
        card.cameraDistance = card.cameraDistance * (resources.displayMetrics.density * 1.33).toFloat()

        buttonLogin.setOnClickListener {
            presenter.onLoginTap(
                emailInput.text.toString(),
                passwordInput.text.toString()
            )
        }

        buttonChangeMode.setOnClickListener {
            presenter.onChangeModeTap()
        }
    }

    override fun switchToLogin() {
        switchMode(getString(R.string.login), getString(R.string.don_t_have_an_account_register), -1)
    }

    override fun switchToRegister() {
        switchMode(getString(R.string.register), getString(R.string.already_have_account), 1)
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

    override fun showProgress() {
        snackbar.show()
    }

    override fun hideProgress() {
        snackbar.dismiss()
    }

    override fun goToCamera() {
        navigationController.navigate(R.id.action_loginFragment_to_cameraFragment)
    }

    override fun showRegisterError() {
        Snackbar.make(fragment, getString(R.string.register_error), Snackbar.LENGTH_LONG).apply {
            view.setBackgroundColor(Color.RED)
            show()
        }
    }

    override fun showLoginError() {
        Snackbar.make(fragment, getString(R.string.login_error), Snackbar.LENGTH_LONG).apply {
            view.setBackgroundColor(Color.RED)
            show()
        }
    }

    override fun onStop() {
        presenter.unsubscribe()
        super.onStop()
    }

}
