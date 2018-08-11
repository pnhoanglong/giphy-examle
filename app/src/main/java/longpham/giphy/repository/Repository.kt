package longpham.giphy.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.giphy.sdk.core.models.Images
import com.giphy.sdk.core.network.api.GPHApi
import longpham.giphy.models.GiphyImage
import longpham.giphy.models.Image
import longpham.giphy.util.GiphyConstants
import longpham.giphy.util.LogUtil
import longpham.giphy.util.logException
import javax.inject.Inject

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

class GiphyRepository @Inject constructor(val giphyApi: GPHApi) : IRepository {
    private val TAG = GiphyRepository::class.simpleName!!
    override fun getTrendingImages(limit: Int?, offset: Int?): LiveData<List<GiphyImage>> {
        LogUtil.i(TAG, "getTrendingImages: limit=$limit offset=$offset")
        val liveData = MutableLiveData<List<GiphyImage>>()
        giphyApi.trending(GiphyConstants.IMAGE_TYPE, limit, offset, GiphyConstants.RATING) trendingApi@{ listMediaResponse, throwable ->
            throwable?.logException()
            if (listMediaResponse?.data?.isEmpty() != false) {
                // No images data return -> Return null
                liveData.postValue(null)
                return@trendingApi
            }
            val trendingImages: List<GiphyImage> = listMediaResponse.data.mapNotNull { it?.images?.toGiphyImage()}
            liveData.postValue(trendingImages)
        }
        return liveData
    }

    override fun getRandomImage(tag: String): LiveData<GiphyImage> {
        LogUtil.i(TAG, "getRandomImage: tag=$tag")
        val liveData = MutableLiveData<GiphyImage>()
        giphyApi.random(tag, GiphyConstants.IMAGE_TYPE, GiphyConstants.RATING) randomApi@{ mediaResponse, throwable ->
            throwable?.printStackTrace()
            liveData.postValue(mediaResponse?.data?.images?.toGiphyImage())
        }
        return liveData
    }

    /**
     * Convert from Giphy API Media Response Object to GiphyImage Model
     */
    private fun Images.toGiphyImage(): GiphyImage? {
        var stillImage: Image?
        var gifImage: Image?

        val giphyStillImages = listOf(fixedWidthStill, fixedWidthSmallStill, fixedHeightStill,
                fixedHeightSmallStill, downsizedStill, originalStill)
        stillImage = createImageModel(giphyStillImages)

        val giphyGifImages = listOf(fixedWidth, fixedWidthSmall, fixedHeight, fixedHeightSmall,
                fixedWidthDownsampled, fixedHeightDownsampled, downsized, downsizedSmall, downsizedMedium, downsizedLarge)
        gifImage = createImageModel(giphyGifImages)
        if (stillImage == null || gifImage == null) {
            return null
        }
        return GiphyImage(stillImage = stillImage, gifImage = gifImage)
    }

    /**
     * Create a Image Model from the first not null Giphy Images
     */
    private fun createImageModel(giphyImages: List<com.giphy.sdk.core.models.Image?>): Image? {
        var imageModel: Image? = null
        giphyImages.firstOrNull { it != null }?.let {
            imageModel = Image(url = it.gifUrl, with = it.width, height = it.height)

        }
        return imageModel
    }
}