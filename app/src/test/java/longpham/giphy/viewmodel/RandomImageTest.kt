package longpham.giphy.viewmodel

import junit.framework.Assert.assertNotNull
import longpham.giphy.BaseViewModelTestSuite
import longpham.giphy.createSelectedImageObserver
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class RandomImageTest: BaseViewModelTestSuite(MockRepository()) {

    @Test
    fun testLiveDataNotNull(){
        assertNotNull(viewModel.images)
    }


    @Test
    fun testSelectingImage(){
        val observer = viewModel.createSelectedImageObserver()
        viewModel.setSelectedImage(MockRepository.image)
        verify(observer).onChanged(MockRepository.image)
    }

    @Test
    fun testLoadingRandomImage(){
        val observer = viewModel.createSelectedImageObserver()
        viewModel.getNextRandomImage()
        verify(observer).onChanged(MockRepository.image)

    }
}