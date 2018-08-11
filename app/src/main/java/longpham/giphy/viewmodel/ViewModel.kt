package longpham.giphy.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import longpham.giphy.models.GiphyImagesObject
import longpham.giphy.repository.IRepository
import longpham.giphy.util.AppConstants
import longpham.giphy.util.GiphyConstants
import java.util.*
import javax.inject.Inject

class ViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {
    private val _imagesLiveData = MutableLiveData<MutableList<GiphyImagesObject>>()
    val images: LiveData<MutableList<GiphyImagesObject>>
        get() = _imagesLiveData


    private val _selectedImageLiveData = MutableLiveData<GiphyImagesObject>()
    val selectedImage: LiveData<GiphyImagesObject>
        get() = _selectedImageLiveData


    private val loadedImages = mutableListOf<GiphyImagesObject>()

    private var loadingImages = false
    private var offset = 0
    @Synchronized
    fun loadTrendingImages(limit: Int = AppConstants.LOAD_MORE_ITEMS_COUNT) {
        if (loadingImages) return // only process one loading  images task

        loadingImages = true
        repository.getTrendingImages(limit = limit, offset = offset).let {
            it.observeForever(object : Observer<List<GiphyImagesObject>?> {
                override fun onChanged(images: List<GiphyImagesObject>?) {
                    if (images?.isEmpty() == false) {
                        loadedImages.addAll(images)
                        _imagesLiveData.postValue(loadedImages)
                        offset += AppConstants.LOAD_MORE_ITEMS_COUNT
                    }
                    it.removeObserver(this)
                    loadingImages = false
                }
            })
        }
    }


    fun getNextRandomImage() {
        val imageTag = selectedImage.value?.tag ?: GiphyConstants.DEFAULT_IMAGE_TAG
        repository.getRandomImage(imageTag).let {
            it.observeForever(object : Observer<GiphyImagesObject?> {
                override fun onChanged(t: GiphyImagesObject?) {
                    t?.apply {
                        _selectedImageLiveData.postValue(this)
                    }
                    it.removeObserver { this }
                }
            })
        }
    }


    fun setSelectedImage(image: GiphyImagesObject) {
        _selectedImageLiveData.postValue(image)
    }
}
