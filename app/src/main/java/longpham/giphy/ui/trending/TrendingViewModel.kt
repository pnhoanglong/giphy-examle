package longpham.giphy.ui.trending

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import longpham.giphy.models.GiphyImage
import longpham.giphy.repository.IRepository
import longpham.giphy.util.AppConstants
import javax.inject.Inject

class TrendingViewModel @Inject constructor(private val repository: IRepository): ViewModel() {
    private val _imagesLiveData = MutableLiveData<MutableList<GiphyImage>>()
    val images:  LiveData<MutableList<GiphyImage>>
        get() = _imagesLiveData

    private val loadedImages = mutableListOf<GiphyImage>()
    var offset = 0

    @Synchronized
    fun loadTrendingImages(limit: Int = AppConstants.LOAD_MORE_ITEMS_COUNT){
        val liveData = repository.getTrendingImages(limit = limit,  offset = offset)
        val observer = Observer<List<GiphyImage>?> { images ->
            if (images?.isEmpty() == false){
                loadedImages.addAll(images)
                _imagesLiveData.postValue(loadedImages)
            }
        }
        liveData.observeForever(observer)
        offset += AppConstants.LOAD_MORE_ITEMS_COUNT
    }
}