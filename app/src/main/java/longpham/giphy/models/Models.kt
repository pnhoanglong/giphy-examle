package longpham.giphy.models

import com.giphy.sdk.core.models.Images
import com.giphy.sdk.core.models.Media
import longpham.giphy.util.GiphyConstants

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
 * Convert from Giphy API Media Response Object to GiphyImagesObject Model
 */

fun Media?.toGiphyImage(): GiphyImagesObject? {
    if (this == null) return null

    val tag = if (tags?.isEmpty() == false) tags!![0] else GiphyConstants.DEFAULT_IMAGE_TAG
    return images.toGiphyImage(tag)
}


private fun Images.toGiphyImage(imageTag: String): GiphyImagesObject? {
    var stillImage: GiphyImage?
    var gifImage: GiphyImage?

    val giphyStillImages = listOf(fixedWidthStill, fixedWidthSmallStill, fixedHeightStill,
            fixedHeightSmallStill, downsizedStill, originalStill)
    stillImage = createImageModel(giphyStillImages)

    val giphyGifImages = listOf(fixedWidth, fixedWidthSmall, fixedHeight, fixedHeightSmall,
            fixedWidthDownsampled, fixedHeightDownsampled, downsized, downsizedSmall, downsizedMedium, downsizedLarge)
    gifImage = createImageModel(giphyGifImages)
    if (stillImage == null || gifImage == null) {
        return null
    }
    return GiphyImagesObject(stillImage = stillImage, gifImage = gifImage, tag = imageTag)
}

/**
 * Create a Image Model from the first not null Giphy Images
 */
private fun createImageModel(giphyImages: List<com.giphy.sdk.core.models.Image?>): GiphyImage? {
    var imageModel: GiphyImage? = null
    giphyImages.firstOrNull { it != null }?.let {
        imageModel = GiphyImage(url = it.gifUrl, with = it.width, height = it.height)

    }
    return imageModel
}