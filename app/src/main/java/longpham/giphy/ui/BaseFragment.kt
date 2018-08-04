package longpham.giphy.ui

import android.content.Context
import android.support.v4.app.Fragment
import longpham.giphy.MainActivity

open class BaseFragment: Fragment() {
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
    }

    fun startNewFragment(newFragment: BaseFragment) = mainActivity.startNewFragment(newFragment)
}