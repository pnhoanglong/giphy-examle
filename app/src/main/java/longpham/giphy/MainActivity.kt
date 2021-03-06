package longpham.giphy

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import longpham.giphy.ui.common.BaseFragment
import longpham.giphy.ui.trending.TrendingFragment
import javax.inject.Inject

/**
 * Application Main Activity.
 */
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var networkErrorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        networkErrorTextView = findViewById(R.id.network_error_textview)
        checkNetworkConnection()

        supportFragmentManager
                .beginTransaction()
                .add(R.id.main_content, TrendingFragment.getInstance())
                .commit()
    }

    fun startNewFragment(newFragment: BaseFragment) {
        val fragmentTag = newFragment.toString()
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_content, newFragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    /***Monitor network connectivity***/
    private val _networkConnectivity = MutableLiveData<Boolean>()
    val networkConnectivityLiveData: LiveData<Boolean>
        get() = _networkConnectivity

    private fun checkNetworkConnection(): Boolean {
        fun isConnected(): Boolean {
            val activeNetwork = (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.activeNetworkInfo
                    ?: return false
            return when (activeNetwork.type) {
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                else -> false
            }
        }

        fun informNetworkConnectivity(connected: Boolean) {
            networkErrorTextView.visibility = if (connected) View.GONE else View.VISIBLE
        }

        val isConnected = isConnected()
        informNetworkConnectivity(isConnected)
        _networkConnectivity.value = isConnected
        return isConnected
    }

    private val networkChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            checkNetworkConnection()
        }
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter().apply { this.addAction(ConnectivityManager.CONNECTIVITY_ACTION) }
        registerReceiver(networkChangeReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)
    }
}
