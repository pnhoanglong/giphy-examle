package longpham.giphy.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.giphy.sdk.core.network.api.GPHApi
import com.giphy.sdk.core.network.api.GPHApiClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import longpham.giphy.MainActivity
import longpham.giphy.repository.GiphyRepository
import longpham.giphy.repository.IRepository
import longpham.giphy.ui.randomimage.RandomImageFragment
import longpham.giphy.ui.randomimage.RandomImageViewModel
import longpham.giphy.ui.trending.TrendingFragment
import longpham.giphy.ui.trending.TrendingViewModel
import longpham.giphy.util.GiphyConstants
import longpham.giphy.viewmodel.ViewModelFactory
import longpham.giphy.viewmodel.ViewModelKey
import javax.inject.Singleton

/**
 * This class provides application level's dependencies
 */
@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGiphyApiClient(): GPHApi = GPHApiClient(GiphyConstants.API_KEY)
    @Singleton

    @Provides
    fun provideRepository(giphyRepository: GiphyRepository): IRepository = giphyRepository
}

/**
 * These two below classes declare activities and fragments to which dependencies are injected
 */
@Module
abstract class MainActivityModule{
    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    abstract fun contributeMainActivity(): MainActivity
}

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeTrendingFragment(): TrendingFragment

    @ContributesAndroidInjector
    abstract fun contributeImageFragment(): RandomImageFragment
}


/**
 * This class provides dependencies for fragments
 */
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TrendingViewModel::class)
    abstract fun bindTrendingViewModel(trendingViewModel: TrendingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RandomImageViewModel::class)
    abstract fun bindRandomImageViewModel(randomImageViewModel: RandomImageViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
