package longpham.giphy.ui.randomimage

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import longpham.giphy.R
import longpham.giphy.databinding.ImageFragmentBinding
import longpham.giphy.di.Injectable
import longpham.giphy.ui.common.BaseFragment
import longpham.giphy.ui.common.GlideApp
import longpham.giphy.util.AppConstants
import longpham.giphy.util.LogUtil
import java.util.*
import javax.inject.Inject
class RandomImageFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RandomImageViewModel

    private lateinit var binding: ImageFragmentBinding

    private var currentImageTag = ""
    private var timer: Timer? = null
    private var loadingRandomImageDelay = AppConstants.RANDOM_IMAGE_INTERVAL

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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get((RandomImageViewModel::class.java))

        arguments?.let {
             it.getString(KEY_SELECTED_IMAGE_URL)?.apply {
                 displayImage(this)
             }
            currentImageTag = it.getString(KEY_SELECTED_IMAGE_TAG) ?: ""
        }

        //Observer random image live data
        viewModel.randomImageLiveData.observe(this, Observer { image ->
            if (image != null) {
                LogUtil.d("Display random image: ${image.gifImage.url}")
                currentImageTag = image.tag
                displayImage(image.gifImage.url)
            }
        })

        //Observer network connectivity
        networkConnectivityLiveData.observe(this, Observer { isConnected ->
            if (!isConnected!!) {
                timer?.cancel()
                loadingRandomImageDelay = 0
                return@Observer
            }

            timer = Timer()
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    viewModel.imageTagLiveData.postValue(currentImageTag)
                }
            }, loadingRandomImageDelay, AppConstants.RANDOM_IMAGE_INTERVAL)
        })
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }

    private val glideListener = object: RequestListener<Drawable>{
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            LogUtil.e("Failed to image because ${e?.message}")
            loadRandomImage()
            return false
        }

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean)  = false
    }

    private fun loadRandomImage() {
        viewModel.imageTagLiveData.value = currentImageTag
    }

    private fun displayImage(imageUrl: String) {
        GlideApp.with(this)
                .load(imageUrl)
                .fitCenter().listener(glideListener)
                .into(binding.imageView)
    }

    companion object {
        const val KEY_SELECTED_IMAGE_URL = "image_url"
        const val KEY_SELECTED_IMAGE_TAG = "image_tag"

        fun getInstance() = RandomImageFragment()
    }
}