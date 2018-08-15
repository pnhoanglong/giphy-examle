package longpham.giphy.ui.trending

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import longpham.giphy.R
import longpham.giphy.databinding.TrendingFragmentBinding
import longpham.giphy.di.Injectable
import longpham.giphy.models.GiphyImagesObject
import longpham.giphy.repository.IRepository
import longpham.giphy.ui.common.BaseFragment
import longpham.giphy.ui.common.InfiniteScrollListener
import longpham.giphy.ui.image.ImageFragment
import longpham.giphy.util.AppConstants
import longpham.giphy.viewmodel.ViewModel
import javax.inject.Inject

class TrendingFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var repository: IRepository

    private lateinit var viewModel: ViewModel

    private lateinit var binding: TrendingFragmentBinding
    private lateinit var recyclerViewAdapter: ImageRecyclerViewAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val PRELOAD_ADHEAD_ITEMS = 10

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(mainActivity, viewModelFactory)
                .get(ViewModel::class.java)
        recyclerViewAdapter = ImageRecyclerViewAdapter(fragment = this, items = mutableListOf()) { clickedItem ->
            viewModel.selectedImage = clickedItem
            startImageFragment()
        }
        binding.imageRecyclerView.adapter = recyclerViewAdapter

        // Observer images live data
        viewModel.images.observe(this, Observer { images ->
            images?.let {
                recyclerViewAdapter.items.addAll(it)
                recyclerViewAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
        })

        // Make image recycler view can scroll infinite
        binding.imageRecyclerView.apply {
            addOnScrollListener(createInfiniteScrollListener())
            addOnScrollListener(createGlideRecyclerViewIntegrationScrollListener())
        }

        // Load Images from server
        viewModel.loadTrendingImages(requestItemsCount = AppConstants.INIT_LOAD_ITEMS_COUNT)

        //Observer network connectivity
        networkConnectivityLiveData.observe(this, Observer { isNetworkConnected ->
            // Load more image if network changed to connected
            if (!isNetworkConnected!!){
                return@Observer
            }
            viewModel.loadTrendingImages(requestItemsCount = AppConstants.INIT_LOAD_ITEMS_COUNT)
        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        linearLayoutManager = LinearLayoutManager(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.trending_fragment, container, false)
        binding.imageRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }
        return binding.root
    }

    private fun createInfiniteScrollListener(): InfiniteScrollListener =
            object : InfiniteScrollListener(maxItemsPerRequest = AppConstants.INIT_LOAD_ITEMS_COUNT,
                    layoutManager = linearLayoutManager) {
                override fun onScrolledToEnd(firstVisibleItemPosition: Int) {
                    if (firstVisibleItemPosition + AppConstants.LOAD_MORE_ITEMS_COUNT <= recyclerViewAdapter.items.size){
                        //No need to load more item
                        return
                    }

                    viewModel.loadTrendingImages()
                    binding.progressBar.apply {
                        visibility = View.VISIBLE
                        (layoutParams as? ConstraintLayout.LayoutParams)?.topToTop = -1
                    }
                }
            }


    private fun createGlideRecyclerViewIntegrationScrollListener(): RecyclerViewPreloader<GiphyImagesObject> {
       val sizeProvider = ViewPreloadSizeProvider<GiphyImagesObject>()
       return  RecyclerViewPreloader<GiphyImagesObject>(this, recyclerViewAdapter, sizeProvider, PRELOAD_ADHEAD_ITEMS)
    }

    private fun startImageFragment() {
        val imageFragment = ImageFragment.getInstance()
        startNewFragment(imageFragment)
    }

    companion object {
        fun getInstance(): TrendingFragment = TrendingFragment()
    }
}