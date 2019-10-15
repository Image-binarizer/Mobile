package pl.pk.zpi.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pl.pk.zpi.R
import kotlinx.android.synthetic.main.item_gallery.view.*
import java.io.File

class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var photos: List<String> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.loadImage(photos[position])
    }

    inner class ViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {

        fun loadImage(filePath: String) {
            Glide.with(item.context)
                .load(File(filePath))
                .into(item.imageView)
        }

    }
}