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
import longpham.giphy.R
import longpham.giphy.databinding.TrendingFragmentBinding
import longpham.giphy.di.Injectable
import longpham.giphy.repository.IRepository
import longpham.giphy.ui.common.BaseFragment
import longpham.giphy.ui.common.InfiniteScrollListener
import longpham.giphy.ui.image.ImageFragment
import longpham.giphy.util.AppConstants
import longpham.giphy.util.LogUtil
import javax.inject.Inject

class TrendingFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var repository: IRepository

    private lateinit var viewModel: TrendingViewModel

    private lateinit var binding: TrendingFragmentBinding
    private lateinit var recyclerViewAdapter: ImageRecyclerViewAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TrendingViewModel::class.java)
        recyclerViewAdapter = ImageRecyclerViewAdapter(fragment = this, items = mutableListOf())
        binding.imageRecyclerView.adapter = recyclerViewAdapter

        viewModel.images.observe(this, Observer { images ->
            images?.let {
                recyclerViewAdapter.items = it
                recyclerViewAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
        })
        viewModel.loadTrendingImages(limit = AppConstants.INIT_LOAD_ITEMS_COUNT)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        linearLayoutManager = LinearLayoutManager(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.trending_fragment, container, false)
        binding.imageRecyclerView.apply {
            LogUtil.i("addOnScrollListener" )
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            addOnScrollListener(createInfiniteScrollListener())
        }
        return binding.root
    }

    private fun createInfiniteScrollListener(): InfiniteScrollListener =
            object : InfiniteScrollListener(maxItemsPerRequest = AppConstants.INIT_LOAD_ITEMS_COUNT,
                    layoutManager = linearLayoutManager) {
                override fun onScrolledToEnd(firstVisibleItemPosition: Int) {
                    LogUtil.i("onScrolledToEnd")
                    viewModel.loadTrendingImages()
                    binding.progressBar?.apply {
                        visibility = View.VISIBLE
                        (layoutParams as? ConstraintLayout.LayoutParams)?.topToTop = -1
                    }
                }
            }

    //TODO: navigation must be done in Fragment
    private fun startImageFragment() {
        val imageFragment = ImageFragment.getInstance()
        startNewFragment(imageFragment)
    }

    companion object {
        fun getInstance(): TrendingFragment = TrendingFragment()
    }
}