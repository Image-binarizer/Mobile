package pl.pk.zpi.ui.login

interface LoginContract {
    interface View {
        fun switchToLogin()
        fun switchToRegister()
        fun initViews()
        fun goToCamera()
        fun showLoginError()
        fun showProgress()
        fun hideProgress()
        fun showRegisterError()
    }

    interface Presenter {
        fun onViewPresent(view: View)
        fun onLoginTap(email: String, password: String)
        fun onChangeModeTap()
        fun unsubscribe()
    }
}
