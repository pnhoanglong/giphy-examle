package longpham.giphy.ui.common

import android.arch.lifecycle.LiveData
import android.content.Context
import android.support.v4.app.Fragment
import longpham.giphy.MainActivity

open class BaseFragment: Fragment() {
    lateinit var mainActivity: MainActivity

    /**
     * LiveData is used for fragment sub-class to handle network status
     */
    val networkConnectivityLiveData: LiveData<Boolean>
        get() = mainActivity.networkConnectivityLiveData

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
    }

    fun startNewFragment(newFragment: BaseFragment) = mainActivity.startNewFragment(newFragment)
}