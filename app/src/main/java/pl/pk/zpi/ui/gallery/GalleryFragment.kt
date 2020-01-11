package pl.pk.zpi.ui.gallery

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import pl.pk.zpi.R

class GalleryFragment : Fragment(), GalleryContract.View {

    private val presenter: GalleryContract.Presenter by inject()
    private val navigationController: NavController by lazy { findNavController() }
    private val galleryAdapter = GalleryAdapter(this::onItemClick)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_gallery, container, false)

    override fun onStart() {
        super.onStart()
        val type = arguments?.getSerializable(EXTRA_GALLERY_TYPE) as GalleryType? ?: GalleryType.ALL_ORIGINAL
        val imageGroup = arguments?.getString(EXTRA_IMAGE_NAME)

        presenter.onViewPresent(this, imageGroup)

        with(recycler) {
            layoutManager = getLayoutManager(type)
            adapter = galleryAdapter
        }

        refreshLayout.setOnRefreshListener { presenter.fetchImages() }
    }

    private fun onItemClick(imageName: String) {
        navigationController.navigate(
            R.id.action_galleryFragment_self,
            newBundle(GalleryType.GROUP, imageName)
        )
    }

    override fun displayImages(links: List<String>) {
        galleryAdapter.photos = links
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
        refreshLayout.isRefreshing = false
    }

    override fun showEmpty() {
        noImages.visibility = View.VISIBLE
    }

    override fun hideEmpty() {
        noImages.visibility = View.GONE
    }

    override fun showError() {
        Snackbar.make(fragment, getString(R.string.something_went_wrong), Snackbar.LENGTH_LONG).apply {
            view.setBackgroundColor(Color.RED)
            show()
        }
    }

    override fun onStop() {
        presenter.unsubscribe()
        super.onStop()
    }

    private fun getLayoutManager(type: GalleryType) =
        when (type) {
            GalleryType.ALL_ORIGINAL -> GridLayoutManager(context, 2)
            GalleryType.GROUP -> LinearLayoutManager(context)
        }

    companion object {
        private const val EXTRA_GALLERY_TYPE = "EXTRA_GALLERY_TYPE"
        private const val EXTRA_IMAGE_NAME = "EXTRA_IMAGE_NAME"

        fun newBundle(type: GalleryType, imageGroup: String?) = Bundle().apply {
            putSerializable(EXTRA_GALLERY_TYPE, type)
            putString(EXTRA_IMAGE_NAME, imageGroup)
        }
    }
}