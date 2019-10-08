package pl.pk.zpi.ui.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_preview.*
import pl.pk.zpi.R
import java.io.File

class PreviewFragment: Fragment() {

    private val navigationController: NavController by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_preview, container, false)

    override fun onStart() {
        super.onStart()

        Glide.with(requireContext())
            .load(File("${activity?.filesDir}/photo.jpg"))
            .into(imageView)

        close.setOnClickListener {
            navigationController.popBackStack()
        }
    }

    override fun onStop() {

        super.onStop()
    }
}