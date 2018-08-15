package longpham.giphy.viewmodel

import junit.framework.Assert.assertEquals
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
        viewModel.selectedImage = MockRepository.image
        assertEquals(viewModel.selectedImage, MockRepository.image)
    }

    @Test
    fun testLoadingRandomImage(){
        val observer = viewModel.createSelectedImageObserver()
        viewModel.imageTagLiveData.value = "tag"
        verify(observer).onChanged(MockRepository.image)

    }
}