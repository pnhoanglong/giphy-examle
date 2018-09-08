package longpham.giphy.ui.trending

import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import longpham.giphy.models.GiphyImagesObject
import longpham.giphy.repository.IRepository
import longpham.giphy.util.AppConstants
import longpham.giphy.util.GiphyConstants
import javax.inject.Inject

class TrendingViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {
    /**
     * Initialize trending image live data
     */
    // Current api offset param
    private var offset = 0
    // Number of images requested in a request
    private var limit = 0
    // Current offset live used to trigger loading trending image
    private val offsetLiveData = MutableLiveData<Int>()

    // Trending images live data
     val images: LiveData<List<GiphyImagesObject>> = Transformations.switchMap(offsetLiveData) { offset ->
       repository.getTrendingImages(limit = limit, offset = offset)
    }

    @Synchronized
    fun loadTrendingImages(requestItemsCount: Int = AppConstants.ITEMS_PER_REQUEST) {
        if (requestItemsCount <= 0) return // only process one loading  images task
        limit = requestItemsCount
        offsetLiveData. value = offset
        offset += limit
    }
}
