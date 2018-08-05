package longpham.giphy.di

import com.giphy.sdk.core.network.api.GPHApi
import com.giphy.sdk.core.network.api.GPHApiClient
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import longpham.giphy.MainActivity
import longpham.giphy.repository.GiphyRepository
import longpham.giphy.repository.IRepository
import longpham.giphy.ui.image.ImageFragment
import longpham.giphy.ui.trending.TrendingFragment
import longpham.giphy.util.GiphyConstants
import javax.inject.Singleton

@Module(includes = [VieModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGiphyApiClient(): GPHApi = GPHApiClient(GiphyConstants.API_KEY)


    @Singleton
    @Provides
    fun provideRepository(giphyApi: GPHApi): IRepository = GiphyRepository(giphyApi = giphyApi)
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
    abstract fun contributeImageFragment(): ImageFragment
}


@Module
abstract class VieModelModule {}
/**
 * Marks an activity / fragment injectable.
 */
interface Injectable