package pl.pk.zpi.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.pk.zpi.R

class GalleryFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_gallery, container, false)

    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {

        super.onStop()
    }
}