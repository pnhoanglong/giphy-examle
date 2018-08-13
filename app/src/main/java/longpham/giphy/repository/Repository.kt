package longpham.giphy.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.giphy.sdk.core.network.api.GPHApi
import longpham.giphy.models.GiphyImagesObject
import longpham.giphy.models.toGiphyImage
import longpham.giphy.util.GiphyConstants
import longpham.giphy.util.LogUtil
import longpham.giphy.util.logException
import javax.inject.Inject

interface IRepository {
    /**
     * Get trending images from server
     */
    //TODO: Should be change to single event
    fun getTrendingImages(limit: Int?, offset: Int?): LiveData<List<GiphyImagesObject>>


    /**
     * Get a random image from server
     */
    fun getRandomImage(tag: String): LiveData<GiphyImagesObject>
}

class GiphyRepository @Inject constructor(val giphyApi: GPHApi) : IRepository {
    override fun getTrendingImages(limit: Int?, offset: Int?): LiveData<List<GiphyImagesObject>> {
        LogUtil.i("getTrendingImages: limit=$limit offset=$offset")
        val liveData = MutableLiveData<List<GiphyImagesObject>>()
        giphyApi.trending(GiphyConstants.IMAGE_TYPE, limit, offset, GiphyConstants.RATING) trendingApi@{ listMediaResponse, throwable ->
            throwable?.logException()
            if (listMediaResponse?.data?.isEmpty() != false) {
                // No images data return -> Return null
                liveData.postValue(null)
                return@trendingApi
            }
            val trendingImages: List<GiphyImagesObject> = listMediaResponse.data.mapNotNull { it.toGiphyImage()}
            liveData.postValue(trendingImages)
        }
        return liveData
    }

    override fun getRandomImage(tag: String): LiveData<GiphyImagesObject> {
        LogUtil.i("getRandomImage")
        val liveData = MutableLiveData<GiphyImagesObject>()
        giphyApi.random(tag, GiphyConstants.IMAGE_TYPE, GiphyConstants.RATING) randomApi@{ mediaResponse, throwable ->
            throwable?.logException()
            liveData.postValue(mediaResponse?.data?.toGiphyImage())
        }
        return liveData
    }
}