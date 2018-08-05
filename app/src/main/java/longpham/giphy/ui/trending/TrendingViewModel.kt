package longpham.giphy.ui.trending

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import longpham.giphy.models.GiphyImage
import longpham.giphy.models.Image
import longpham.giphy.repository.IRepository
import longpham.giphy.util.AppConstants
import longpham.giphy.util.LogUtil
import javax.inject.Inject

class TrendingViewModel @Inject constructor(private val repository: IRepository): ViewModel() {
    private val _imagesLiveData = MutableLiveData<List<GiphyImage>>()
    val images:  LiveData<List<GiphyImage>>
        get() = _imagesLiveData


    val stillImage = Image(url = "https://4.imimg.com/data4/KQ/QE/ANDROID-40327085/product-500x500.jpeg", with = 0, height = 0)
    val gifImage = Image(url = "https://media2.giphy.com/media/2eLAwdushm3cI/100w.gif", with = 0, height = 0)
    val image = GiphyImage(stillImage = stillImage, gifImage = gifImage)

    private val giphyImages = mutableListOf<GiphyImage>()

    fun loadTrendingImages(){
       repeat(AppConstants.ITEM_PER_REQUEST)  {
           giphyImages.add(image)
       }
        _imagesLiveData.postValue(giphyImages)
    }


    fun test(){
        LogUtil.e("ViewModel", "Test view module $repository")
    }
}