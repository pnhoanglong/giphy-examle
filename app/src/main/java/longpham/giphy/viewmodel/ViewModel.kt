package longpham.giphy.viewmodel

import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import longpham.giphy.models.GiphyImagesObject
import longpham.giphy.repository.IRepository
import longpham.giphy.util.AppConstants
import longpham.giphy.util.GiphyConstants
import javax.inject.Inject

class ViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

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

    /**
     * Initialize random image live data
     */
    // Image tag used for giphy random api
    // To execute load next ramdom image, the `view` change image tag value
    val imageTagLiveData = MutableLiveData<String>()

    // Random image live data
    val selectedImage: LiveData<GiphyImagesObject> = Transformations.switchMap(imageTagLiveData) { imageTag ->
        repository.getRandomImage(imageTag)
    }

    @Synchronized
    fun loadTrendingImages(requestItemsCount: Int = AppConstants.LOAD_MORE_ITEMS_COUNT) {
        if (requestItemsCount <= 0) return // only process one loading  images task
        limit = requestItemsCount
        offset = images.value?.size ?: 0
        offsetLiveData. value = offset
    }
}
