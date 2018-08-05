package longpham.giphy.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.core.network.api.GPHApiClient
import longpham.giphy.models.GiphyImage
import longpham.giphy.models.Image
import longpham.giphy.util.GiphyConstants
import longpham.giphy.util.logException
import javax.inject.Inject
import kotlin.properties.Delegates

interface IRepository {
    /**
     * Get trending images from server
     */
    fun getTrendingImages(limit: Int?, offset: Int?): LiveData<List<GiphyImage>>

    /**
     * Get a random image from server
     */
    fun getRandomImage(tag: String): LiveData<GiphyImage>
}

class GiphyRepository @Inject constructor(val giphyClient: GPHApiClient) : IRepository {
//    val giphyClient = GPHApiClient(GiphyConstants.GIPHY_API_KEY)

    override fun getTrendingImages(limit: Int?, offset: Int?): LiveData<List<GiphyImage>> {
        val liveData = MutableLiveData<List<GiphyImage>>()

        giphyClient.trending(GiphyConstants.IMAGE_TYPE, limit, offset, GiphyConstants.RATING) trendingApi@{ listMediaResponse, throwable ->
            throwable?.logException()
            if (listMediaResponse?.data?.isEmpty() != false) {
                // No images data return -> Return null
                liveData.postValue(null)
                return@trendingApi
            }
            val trendingImages = listMediaResponse.data.map { it.toGiphyImage() }
            liveData.postValue(trendingImages)
        }
        return liveData
    }

    override fun getRandomImage(tag: String): LiveData<GiphyImage> {
        val liveData = MutableLiveData<GiphyImage>()
        giphyClient.random(tag, GiphyConstants.IMAGE_TYPE, GiphyConstants.RATING) randomApi@{ mediaResponse, throwable ->
            throwable?.printStackTrace()
            if (mediaResponse?.data == null) {
                // No images data return -> Return null
                liveData.postValue(null)
                return@randomApi
            }
            val image = mediaResponse.data.toGiphyImage()
            liveData.postValue(image)
        }
        return liveData
    }

    /**
     * Convert from Giphy API Media Response Object to GiphyImage Model
     */
    fun Media.toGiphyImage(): GiphyImage {
        var stillImage: Image by Delegates.notNull()
        var gifImage: Image by Delegates.notNull()
        Media@ this.images.fixedWidthStill.let {
            stillImage = Image(url = it.gifUrl, with = it.width, height = it.height)
        }
        Media@ this.images.downsizedSmall.let {
            gifImage = Image(url = it.gifUrl, with = it.width, height = it.height)
        }
        return GiphyImage(stillImage = stillImage, gifImage = gifImage)
    }
}