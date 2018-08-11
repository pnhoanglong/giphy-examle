package longpham.giphy.ui.image

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import longpham.giphy.R
import longpham.giphy.databinding.ImageFragmentBinding
import longpham.giphy.di.Injectable
import longpham.giphy.ui.common.BaseFragment
import longpham.giphy.ui.common.GlideApp
import longpham.giphy.util.AppConstants
import longpham.giphy.util.LogUtil
import longpham.giphy.viewmodel.ViewModel
import javax.inject.Inject

class ImageFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ViewModel

    private lateinit var binding: ImageFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.image_fragment, container, false)
        GlideApp.with(this)
                .load(R.drawable.loading)
                .fitCenter()
                .into(binding.loadingImageView)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(mainActivity, viewModelFactory)
                .get(ViewModel::class.java)

        viewModel.selectedImage.observe(this, Observer { selectedImage ->
            if (selectedImage == null) return@Observer
            LogUtil.d("Display random image: ${selectedImage!!.gifImage.url}")
            GlideApp.with(this)
                    .load(selectedImage!!.gifImage.url)
                    .fitCenter()
                    .into(binding.imageView)
        })
        loadRandomImage()
    }

    private fun loadRandomImage() {
        if (isDetached) return
        binding.root.postDelayed({
            viewModel.getNextRandomImage()
            loadRandomImage()
        }, AppConstants.RANDOM_IMAGE_INTERVAL)
    }

    companion object {
        fun getInstance(): ImageFragment = ImageFragment()
    }
}