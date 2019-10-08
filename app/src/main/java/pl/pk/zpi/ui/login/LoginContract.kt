package pl.pk.zpi.ui.login

interface LoginContract {
    interface View {
        fun switchToLogin()
        fun switchToRegister()
        fun initViews()
        fun goToCamera()
        fun showError()
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun onViewPresent(view: View)
        fun onLoginTap(email: String, password: String)
        fun onChangeModeTap()
        fun unsubscribe()
    }
}
