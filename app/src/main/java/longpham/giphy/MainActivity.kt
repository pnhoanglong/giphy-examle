package longpham.giphy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import longpham.giphy.ui.BaseFragment
import longpham.giphy.ui.TrendingFragment

class MainActivity : AppCompatActivity() {

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
}
