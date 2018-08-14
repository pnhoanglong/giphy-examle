package longpham.giphy.viewmodel

import junit.framework.Assert.assertNotNull
import longpham.giphy.BaseViewModelTestSuite
import longpham.giphy.createTrendingImagesObserver
import longpham.giphy.models.GiphyImagesObject
import longpham.giphy.util.AppConstants
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class TrendingImagesTest: BaseViewModelTestSuite(MockRepository()) {

    @Test
    fun testLiveDataNotNull(){
        assertNotNull(viewModel.images)
    }

    @Test
    fun testInvalidLoadingTrendingImage(){
        val observer = viewModel.createTrendingImagesObserver()
        viewModel.loadTrendingImages(limit = -1)
        verifyNoMoreInteractions(observer)
        viewModel.loadTrendingImages(limit = 0)
        verifyNoMoreInteractions(observer)

    }

    @Test
    fun testLoadingTrendingImagesOnce(){
        val observer = viewModel.createTrendingImagesObserver()
        viewModel.loadTrendingImages()
        val expectedImages = mutableListOf<GiphyImagesObject>()
        repeat(AppConstants.LOAD_MORE_ITEMS_COUNT) {
            expectedImages.add(MockRepository.image)
        }
        verify(observer).onChanged(expectedImages)
    }

    @Test
    fun testLoadingTrendingImageTwice(){
        val observer = viewModel.createTrendingImagesObserver()
        val expectedImages = mutableListOf<GiphyImagesObject>()
        repeat(15) {
            expectedImages.add(MockRepository.image)
        }
        viewModel.loadTrendingImages(5)
        viewModel.loadTrendingImages(10)
        verify(observer, times(2)).onChanged(expectedImages)
    }
}