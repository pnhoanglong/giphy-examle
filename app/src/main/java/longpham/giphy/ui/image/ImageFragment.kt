package longpham.giphy.ui.image

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import longpham.giphy.R
import longpham.giphy.databinding.ImageFragmentBinding
import longpham.giphy.ui.common.BaseFragment

class ImageFragment: BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<ImageFragmentBinding>(inflater, R.layout.image_fragment, container, false)
        return binding.root
    }

    companion object {
        fun getInstance(): ImageFragment = ImageFragment()
    }
}