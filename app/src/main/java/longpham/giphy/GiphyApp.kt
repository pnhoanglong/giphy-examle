package longpham.giphy

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import longpham.giphy.di.AppInjector
import javax.inject.Inject

class GiphyApp: Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
    override fun activityInjector() = dispatchingAndroidInjector
}