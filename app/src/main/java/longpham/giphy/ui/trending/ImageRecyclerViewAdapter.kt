package longpham.giphy.ui.trending

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import longpham.giphy.databinding.ImageListItemViewBinding
import longpham.giphy.models.GiphyImage
import longpham.giphy.ui.common.DataBoundViewHolder
import longpham.giphy.util.LogUtil

class ImageRecyclerViewAdapter(private val fragment: Fragment, public var items: MutableList<GiphyImage>) :
        RecyclerView.Adapter<DataBoundViewHolder<ImageListItemViewBinding>>(), ListPreloader.PreloadModelProvider<GiphyImage> {


    val TAG = ImageRecyclerViewAdapter::class.simpleName!!

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DataBoundViewHolder<ImageListItemViewBinding>, position: Int) {
        val imageView = holder.binding.trendingImageView
        val imageUrl = items[position].stillImage.url
        LogUtil.i("LoadImageUrl: $imageUrl")
        Glide.with(fragment).load(imageUrl).into(imageView)
        holder.binding.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<ImageListItemViewBinding> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ImageListItemViewBinding.inflate(layoutInflater, parent, false)
        return DataBoundViewHolder(binding)
    }

    /*
        Implement ListPreloader.PreloadModelProvider interface
     */
    override fun getPreloadItems(position: Int): MutableList<GiphyImage> =
            if (items.size <= position) {
                mutableListOf()
            } else {
                mutableListOf(items[position])
            }

    override fun getPreloadRequestBuilder(item: GiphyImage): RequestBuilder<*>?  =
            Glide.with(fragment).load(item.stillImage.url)
}

