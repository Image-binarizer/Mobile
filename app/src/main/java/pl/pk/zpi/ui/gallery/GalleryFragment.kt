package pl.pk.zpi.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_gallery.*
import org.koin.android.ext.android.inject
import pl.pk.zpi.R

class GalleryFragment: Fragment(), GalleryContract.View {

    private val presenter: GalleryContract.Presenter by inject()
    private val galleryAdapter = GalleryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_gallery, container, false)

    override fun onStart() {
        super.onStart()
        presenter.onViewPresent(this)

        with(recycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = galleryAdapter
        }
    }

    override fun displayImages(links: List<String>) {
        galleryAdapter.photos = links
    }

    override fun showError() {
        Toast.makeText(context, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        presenter.unsubscribe()
        super.onStop()
    }
}