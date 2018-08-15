package longpham.giphy.viewmodel

import longpham.giphy.BaseViewModelTestSuite
import longpham.giphy.createSelectedImageObserver
import longpham.giphy.createTrendingImagesObserver
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

@RunWith(JUnit4::class)
class NoDataRepositoryTest: BaseViewModelTestSuite(NoDataRepository()) {

    @Test
    fun testTrendingImage(){
        val observer = viewModel.createTrendingImagesObserver()
        viewModel.loadTrendingImages(10)
        verify(observer).onChanged(null)
    }

    @Test
    fun testRandomImage(){
        val observer = viewModel.createSelectedImageObserver()
        viewModel.imageTagLiveData.value = "image"
        verify(observer).onChanged(null)
    }
}


