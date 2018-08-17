package longpham.giphy.models

import com.giphy.sdk.core.models.Image
import com.giphy.sdk.core.models.Images
import com.giphy.sdk.core.models.Media
import longpham.giphy.util.GiphyConstants
import longpham.giphy.util.LogUtil

/**
 * Data class hold an image which can be a still image or gif info.
 */
data class GiphyImage (
        val url : String,
        val with: Int,
        val height: Int
)

/**
 * Data class hold an Giphy image which includes a still image and a gif image info.
 */
data class GiphyImagesObject (
        val stillImage: GiphyImage,
        val gifImage: GiphyImage,
        val tag: String
)

/**
 * Convert from Giphy API Media Response Object to GiphyImagesObject Model.
 */

fun Media?.toGiphyImage(): GiphyImagesObject? {
    if (this == null) return null

    val tag = if (tags?.isEmpty() == false) tags!![0] else GiphyConstants.DEFAULT_IMAGE_TAG
    return images.toGiphyImage(tag)
}


fun Images?.toGiphyImage(imageTag: String): GiphyImagesObject? {
    if (this == null) return null

    val stillImage: GiphyImage?
    val gifImage: GiphyImage?

    val giphyStillImages = listOf(fixedWidthStill, fixedWidthSmallStill, fixedHeightStill,
            fixedHeightSmallStill, downsizedStill, originalStill)
    stillImage = giphyStillImages.createImageModel()

    val giphyGifImages = listOf(downsized, downsizedSmall, downsizedMedium, downsizedLarge,
            fixedWidth, fixedHeight, original, fixedWidthDownsampled, fixedHeightDownsampled)
    gifImage = giphyGifImages.createImageModel()
    if (stillImage == null || gifImage == null) {
        return null
    }
    return GiphyImagesObject(stillImage = stillImage, gifImage = gifImage, tag = imageTag)
}

/**
 * Create a Image Model from the first not null Giphy Images
 */
fun List<Image?>.createImageModel(): GiphyImage? {
    firstOrNull {!it?.gifUrl.isNullOrEmpty()}?.let {
        return GiphyImage(url = it.gifUrl, with = it.width, height = it.height)
    }
    return null
}