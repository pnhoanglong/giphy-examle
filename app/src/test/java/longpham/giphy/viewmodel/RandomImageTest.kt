package longpham.giphy.viewmodel

import junit.framework.Assert.assertNotNull
import longpham.giphy.BaseViewModelTestSuite
import longpham.giphy.createSelectedImageObserver
import longpham.giphy.ui.randomimage.RandomImageViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class RandomImageTest: BaseViewModelTestSuite() {
    private lateinit var viewModel: RandomImageViewModel
    private lateinit var noDataViewModel: RandomImageViewModel

    @Before
    fun init(){
        viewModel = RandomImageViewModel(MockRepository())
        noDataViewModel = RandomImageViewModel(NoDataRepository())
    }

    @Test
    fun testLiveDataNotNull(){
        assertNotNull(viewModel.imageTagLiveData)
        assertNotNull(viewModel.randomImageLiveData)
    }

    @Test
    fun testLoadingRandomImage(){
        val observer = viewModel.createSelectedImageObserver()
        viewModel.imageTagLiveData.value = "tag"
        verify(observer).onChanged(MockRepository.image)

    }

    @Test
    fun testRandomImageWithNoDataRepo(){
        val observer = noDataViewModel.createSelectedImageObserver()
        noDataViewModel.imageTagLiveData.value = "image"
        verify(observer).onChanged(null)
    }
}