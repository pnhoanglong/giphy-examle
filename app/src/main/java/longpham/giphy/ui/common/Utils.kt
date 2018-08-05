package longpham.giphy.ui.common

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * Wrapper to use ViewBinding as ViewHolder
 */
class DataBoundViewHolder<out T : ViewDataBinding> constructor(val binding: T) :
        RecyclerView.ViewHolder(binding.root)