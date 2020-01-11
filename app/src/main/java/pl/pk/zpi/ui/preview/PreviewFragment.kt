package pl.pk.zpi.ui.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_preview.*
import org.koin.android.ext.android.inject
import pl.pk.zpi.R
import java.io.File

class PreviewFragment : Fragment(), PreviewContract.View {

    private val presenter: PreviewContract.Presenter by inject()
    private val navigationController: NavController by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_preview, container, false)

    override fun onStart() {
        super.onStart()
        presenter.onViewPresent(this, "${requireActivity().filesDir}/${arguments?.getString(FILE_NAME_EXTRA)}")

        close.setOnClickListener { presenter.onCloseTap() }
        sendButton.setOnClickListener { presenter.onSendTap() }
    }

    override fun displayPhoto(fileName: String) {
        Glide.with(requireContext())
            .load(File(fileName))
            .into(imageView)
    }

    override fun showError() {
        Toast.makeText(context, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun goBack() {
        navigationController.popBackStack()
    }

    override fun onStop() {
        presenter.unsubscribe()
        super.onStop()
    }

    companion object {
        private const val FILE_NAME_EXTRA = "FILE_NAME_EXTRA"

        fun newBundle(fileName: String) = Bundle().apply {
            putString(FILE_NAME_EXTRA, fileName)
        }
    }
}