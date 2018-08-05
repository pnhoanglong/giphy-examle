package longpham.giphy.ui.trending

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import longpham.giphy.databinding.ImageListItemViewBinding
import longpham.giphy.models.GiphyImage
import longpham.giphy.ui.common.DataBoundViewHolder

class ImageRecyclerViewAdapter(private val fragment: Fragment, private val items: MutableList<GiphyImage>) :
        RecyclerView.Adapter<DataBoundViewHolder<ImageListItemViewBinding>>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DataBoundViewHolder<ImageListItemViewBinding>, position: Int) {
        val imageView = holder.binding.trendingImageView
        val imageUrl = items[position].stillImage.url
        Glide.with(fragment).load(imageUrl).into(imageView)
        holder.binding.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<ImageListItemViewBinding> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ImageListItemViewBinding.inflate(layoutInflater, parent, false)
        return DataBoundViewHolder(binding)
    }

    fun addItems(newItems: List<GiphyImage>) {
        items.addAll(newItems)
    }
}

