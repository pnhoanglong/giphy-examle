package longpham.giphy.ui.trending

import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import longpham.giphy.databinding.ImageListItemViewBinding
import longpham.giphy.models.GiphyImagesObject
import longpham.giphy.ui.common.DataBoundViewHolder
import longpham.giphy.ui.common.GlideApp
import longpham.giphy.ui.common.getScreenWidth
import longpham.giphy.util.AppConstants

class ImageRecyclerViewAdapter(private val fragment: Fragment, var items: MutableList<GiphyImagesObject>,
                               val itemViewOnClickListener: ((GiphyImagesObject) -> Unit)? = null) :
        RecyclerView.Adapter<DataBoundViewHolder<ImageListItemViewBinding>>(), ListPreloader.PreloadModelProvider<GiphyImagesObject> {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DataBoundViewHolder<ImageListItemViewBinding>, position: Int) {
        val imageView = holder.binding.trendingImageView
        val imageUrl = items[position].stillImage.url
        buildLoadImageRequest(fragment = fragment, imageUrl = imageUrl).into(imageView)
        holder.binding.root.setOnClickListener{
            itemViewOnClickListener?.invoke(items[position])
        }
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
            GlideApp.with(fragment).load(imageUrl).override( getScreenWidth(), AppConstants.MAX_IMAGE_HEIGHT)
}
