package longpham.giphy.di

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
import longpham.giphy.ui.image.RandomImageFragment
import longpham.giphy.ui.trending.TrendingFragment
import longpham.giphy.util.GiphyConstants
import longpham.giphy.viewmodel.ViewModel
import longpham.giphy.viewmodel.ViewModelFactory
import longpham.giphy.viewmodel.ViewModelKey
import javax.inject.Singleton

@Module(includes = [VieModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGiphyApiClient(): GPHApi = GPHApiClient(GiphyConstants.API_KEY)
    @Singleton

    @Provides
    fun provideRepository(giphyRepository: GiphyRepository): IRepository = giphyRepository
//    fun provideRepository(giphyRepository: MockRepository): IRepository = giphyRepository
}

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

@Module
abstract class VieModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ViewModel::class)
    abstract fun bindTrendingViewModel(trendingViewModel: ViewModel): android.arch.lifecycle.ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
/**
 * Marks an activity / fragment injectable.
 */
interface Injectable