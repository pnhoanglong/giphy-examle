package longpham.giphy.ui.trending

import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import longpham.giphy.R
import longpham.giphy.databinding.ImageListItemViewBinding
import longpham.giphy.models.GiphyImagesObject
import longpham.giphy.ui.common.DataBoundViewHolder
import longpham.giphy.ui.common.GlideApp
import longpham.giphy.util.LogUtil

class ImageRecyclerViewAdapter(private val fragment: Fragment, public var items: MutableList<GiphyImagesObject>) :
        RecyclerView.Adapter<DataBoundViewHolder<ImageListItemViewBinding>>(), ListPreloader.PreloadModelProvider<GiphyImagesObject> {


    val TAG = ImageRecyclerViewAdapter::class.simpleName!!

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DataBoundViewHolder<ImageListItemViewBinding>, position: Int) {
        val imageView = holder.binding.trendingImageView
        val imageUrl = items[position].stillImage.url
        LogUtil.d("LoadImageUrl: $imageUrl")
        holder.binding.orderNumberTextView.text = position.toString()
        buildLoadImageRequest(fragment = fragment, imageUrl = imageUrl).into(imageView)
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
    override fun getPreloadItems(position: Int): MutableList<GiphyImagesObject> =
            if (items.size <= position) {
                mutableListOf()
            } else {
                mutableListOf(items[position])
            }

    override fun getPreloadRequestBuilder(item: GiphyImagesObject): RequestBuilder<*>?  =
            buildLoadImageRequest(fragment = fragment, imageUrl = item.stillImage.url)


    private fun buildLoadImageRequest(fragment: Fragment, imageUrl: String): RequestBuilder<Drawable> =
            GlideApp.with(fragment).load(imageUrl).placeholder(R.drawable.placeholder).centerCrop()
}

