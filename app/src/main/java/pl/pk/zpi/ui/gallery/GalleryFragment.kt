package pl.pk.zpi.ui.gallery

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_login.*
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

        refreshLayout.setOnRefreshListener { presenter.fetchImages() }
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
}