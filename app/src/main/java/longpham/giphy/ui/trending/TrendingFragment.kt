package longpham.giphy.ui.trending

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import longpham.giphy.R
import longpham.giphy.databinding.TrendingFragmentBinding
import longpham.giphy.di.Injectable
import longpham.giphy.models.GiphyImage
import longpham.giphy.models.Image
import longpham.giphy.ui.common.BaseFragment
import longpham.giphy.ui.image.ImageFragment

class TrendingFragment : BaseFragment(), Injectable {

    private lateinit var binding: TrendingFragmentBinding
    private lateinit var recyclerViewAdapter: ImageRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val images = mutableListOf<GiphyImage>()
        repeat(10) {
            images.add(GiphyImage(
                    stillImage = Image(url = "https://4.imimg.com/data4/KQ/QE/ANDROID-40327085/product-500x500.jpeg", with = 0, height = 0),
                    gifImage = Image(url = "https://media2.giphy.com/media/2eLAwdushm3cI/100w.gif", with = 0, height = 0)
            ))
        }
        recyclerViewAdapter = ImageRecyclerViewAdapter(fragment = this, items = images)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.trending_fragment, container, false)
        binding.imageRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }
        return binding.root
    }

    private fun startImageFragment() {
        val imageFragment = ImageFragment.getInstance()
        startNewFragment(imageFragment)
    }

    companion object {
        fun getInstance(): TrendingFragment = TrendingFragment()
    }
}