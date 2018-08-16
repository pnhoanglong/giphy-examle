package longpham.giphy.viewmodel

import junit.framework.Assert.assertNotNull
import longpham.giphy.BaseViewModelTestSuite
import longpham.giphy.createTrendingImagesObserver
import longpham.giphy.models.GiphyImagesObject
import longpham.giphy.ui.image.RandomImageViewModel
import longpham.giphy.ui.trending.TrendingViewModel
import longpham.giphy.util.AppConstants
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class TrendingImagesTest: BaseViewModelTestSuite() {
    private lateinit var viewModel: TrendingViewModel
    private lateinit var noDataViewModel: TrendingViewModel

    @Before
    fun init(){
        viewModel = TrendingViewModel(MockRepository())
        noDataViewModel = TrendingViewModel(NoDataRepository())
    }
    @Test
    fun testLiveDataNotNull(){
        assertNotNull(viewModel.images)
    }

    @Test
    fun testInvalidLoadingTrendingImage(){
        val observer = viewModel.createTrendingImagesObserver()
        viewModel.loadTrendingImages(requestItemsCount = -1)
        verifyNoMoreInteractions(observer)
        viewModel.loadTrendingImages(requestItemsCount = 0)
        verifyNoMoreInteractions(observer)

    }

    @Test
    fun testLoadingTrendingImagesOnce(){
        val observer = viewModel.createTrendingImagesObserver()
        viewModel.loadTrendingImages()
        val expectedImages = mutableListOf<GiphyImagesObject>()
        repeat(AppConstants.ITEMS_PER_REQUEST) {
            expectedImages.add(MockRepository.image)
        }
        verify(observer).onChanged(expectedImages)
    }

    @Test
    fun testLoadingTrendingImageTwice(){
        val observer = viewModel.createTrendingImagesObserver()
        viewModel.loadTrendingImages(1)
        viewModel.loadTrendingImages(2)
        //Verify the first onChanged
        verify(observer).onChanged(mutableListOf(MockRepository.image))
        //Verify the second onChanged
        val expectedImages = mutableListOf<GiphyImagesObject>()
        repeat(2) {
            expectedImages.add(MockRepository.image)
        }
        verify(observer).onChanged(expectedImages)
    }

    @Test
    fun testTrendingImageWithNoDataRepo(){
        val observer = noDataViewModel.createTrendingImagesObserver()
        noDataViewModel.loadTrendingImages(10)
        verify(observer).onChanged(null)
    }
}