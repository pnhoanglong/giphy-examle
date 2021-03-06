package longpham.giphy.ui.randomimage

import android.arch.lifecycle.*
import android.arch.lifecycle.ViewModel
import longpham.giphy.models.GiphyImagesObject
import longpham.giphy.repository.IRepository
import javax.inject.Inject

/**
 * ViewModel provides observable data for Random Image screen.
 */
class RandomImageViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

    /**
     * Initialize random image live data.
     */
    // Image tag used for giphy random api
    // To execute load next ramdom image, the `view` change image tag value
    val imageTagLiveData = MutableLiveData<String>()

    // Random image live data
    val randomImageLiveData: LiveData<GiphyImagesObject> = Transformations.switchMap(imageTagLiveData) { imageTag ->
        repository.getRandomImage(imageTag)
    }
}
