package longpham.giphy

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import longpham.giphy.ui.common.BaseFragment
import longpham.giphy.ui.trending.TrendingFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
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
}
