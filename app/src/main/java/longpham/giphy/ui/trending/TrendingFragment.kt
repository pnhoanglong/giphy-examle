package longpham.giphy.ui.trending

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import longpham.giphy.R
import longpham.giphy.databinding.TrendingFragmentBinding
import longpham.giphy.ui.common.BaseFragment
import longpham.giphy.ui.image.ImageFragment

class TrendingFragment: BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<TrendingFragmentBinding>(inflater, R.layout.trending_fragment, container, false)
        binding.button.setOnClickListener {
            startImageFragment()
        }
        return binding.root
    }



    private fun startImageFragment(){
        val imageFragment = ImageFragment.getInstance()
        startNewFragment(imageFragment)
    }

    companion object {
        fun getInstance(): TrendingFragment = TrendingFragment()
    }
}