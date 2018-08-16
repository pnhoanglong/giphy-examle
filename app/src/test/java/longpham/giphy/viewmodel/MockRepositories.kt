package longpham.giphy.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import longpham.giphy.models.GiphyImagesObject
import longpham.giphy.models.GiphyImage
import longpham.giphy.repository.IRepository
import longpham.giphy.util.AppConstants
import javax.inject.Inject

class MockRepository: IRepository {

    override fun getRandomImage(tag: String): LiveData<GiphyImagesObject> {
        val liveData = MutableLiveData<GiphyImagesObject>()
        liveData.value = image
        return liveData
    }

    override fun getTrendingImages(limit: Int?, offset: Int?): LiveData<List<GiphyImagesObject>> {
        val liveData = MutableLiveData<List<GiphyImagesObject>>()
        val giphyImages = mutableListOf<GiphyImagesObject>()
        repeat(limit?: AppConstants.ITEMS_PER_REQUEST) {
            giphyImages.add(image)
        }
        liveData.value = giphyImages
        return liveData
    }

    companion object {
        val stillImage = GiphyImage(url = "https://4.imimg.com/data4/KQ/QE/ANDROID-40327085/product-500x500.jpeg", with = 0, height = 0)
        val gifImage = GiphyImage(url = "https://media2.giphy.com/media/2eLAwdushm3cI/100w.gif", with = 0, height = 0)
        val image = GiphyImagesObject(stillImage = stillImage, gifImage = gifImage, tag = "tag")

    }
}

class NoDataRepository: IRepository {
    override fun getTrendingImages(limit: Int?, offset: Int?): LiveData<List<GiphyImagesObject>> {
        val liveData = MutableLiveData<List<GiphyImagesObject>>()
        liveData.value = null
        return liveData
    }

    override fun getRandomImage(tag: String): LiveData<GiphyImagesObject> {
        val liveData = MutableLiveData<GiphyImagesObject>()
        liveData.value = null
        return liveData
    }
}
