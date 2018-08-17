package longpham.giphy.models

import com.giphy.sdk.core.models.Image
import com.giphy.sdk.core.models.Images
import com.giphy.sdk.core.models.Media
import junit.framework.TestCase.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class ModelTests {
    val giphySdkImage = mock(Image::class.java)
    val nullUrlImge = mock(Image::class.java)
    val nullImage: Image? = null
    val imageUrl = "not null url"
    val tag = "tag"

    init {
        `when`(giphySdkImage.gifUrl).thenReturn(imageUrl)
    }

    @Test
    fun testCreateImageModelFromSdkImages() {
        val sdkImage = mutableListOf(nullImage, nullUrlImge)
        assertNull(sdkImage.createImageModel())

        sdkImage.add(giphySdkImage)
        val giphyImage = sdkImage.createImageModel()
        assertNotNull(giphyImage)
        assertEquals(imageUrl, giphyImage?.url)
    }

    @Test
    fun testConvertFromSdkImagesToGiphyImgesObject() {
        val sdkImages = mock(Images::class.java)
        assertNull(sdkImages.toGiphyImage(tag))

        `when`(sdkImages.fixedWidthStill).thenReturn(giphySdkImage)
        assertNull(sdkImages.toGiphyImage(tag))

        `when`(sdkImages.fixedHeightDownsampled).thenReturn(giphySdkImage)
        val giphyImage = sdkImages.toGiphyImage(tag)
        verifyGiphyImagesObject(giphyImage!!)
    }

    @Test
    fun testConvertFromSdkMediaToGiphyImage() {
        val sdkMedia = mock(Media::class.java)
        assertNull(sdkMedia.toGiphyImage())
        val sdkImages = mock(Images::class.java)
        `when`(sdkImages.fixedWidthStill).thenReturn(giphySdkImage)
        `when`(sdkImages.fixedHeightDownsampled).thenReturn(giphySdkImage)
        `when`(sdkMedia.images).thenReturn(sdkImages)
        `when`(sdkMedia.tags).thenReturn(mutableListOf(tag))
        val giphyImage = sdkMedia.toGiphyImage()
        verifyGiphyImagesObject(giphyImage!!)
    }

    private fun verifyGiphyImagesObject(giphyImage: GiphyImagesObject) {
        assertNotNull(giphyImage)
        assertEquals(tag, giphyImage.tag)
        assertEquals(imageUrl, giphyImage.stillImage.url)
        assertEquals(imageUrl, giphyImage.gifImage.url)
    }
}
