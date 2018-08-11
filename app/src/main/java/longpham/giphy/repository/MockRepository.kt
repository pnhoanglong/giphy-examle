package longpham.giphy.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import longpham.giphy.models.GiphyImage
import longpham.giphy.models.Image
import longpham.giphy.util.AppConstants
import javax.inject.Inject

class MockRepository @Inject constructor(): IRepository {
    private val stillImage = Image(url = "https://4.imimg.com/data4/KQ/QE/ANDROID-40327085/product-500x500.jpeg", with = 0, height = 0)
    private val gifImage = Image(url = "https://media2.giphy.com/media/2eLAwdushm3cI/100w.gif", with = 0, height = 0)
    private val image = GiphyImage(stillImage = stillImage, gifImage = gifImage)

    override fun getRandomImage(tag: String): LiveData<GiphyImage> {
        val liveData = MutableLiveData<GiphyImage>()
        liveData.value = image
        return liveData
    }

    override fun getTrendingImages(limit: Int?, offset: Int?): LiveData<List<GiphyImage>> {
        val liveData = MutableLiveData<List<GiphyImage>>()
        val giphyImages = mutableListOf<GiphyImage>()
        repeat(AppConstants.LOAD_MORE_ITEMS_COUNT) {
            giphyImages.add(image)
        }
        liveData.value = giphyImages
        return liveData
    }
}