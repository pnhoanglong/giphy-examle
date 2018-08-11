package longpham.giphy.ui.common

import android.content.res.Resources
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * Wrapper to use ViewBinding as ViewHolder
 */
class DataBoundViewHolder<out T : ViewDataBinding> constructor(val binding: T) :
        RecyclerView.ViewHolder(binding.root)

@GlideModule
class GiphyAppGlideModule: AppGlideModule() {
}

fun getScreenWidth(): Int =  Resources.getSystem().displayMetrics.widthPixels
