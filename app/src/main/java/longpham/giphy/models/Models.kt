package longpham.giphy.models

/**
 * Data class hold an image which can be a still image or gif info.
 */
data class Image (
        val url : String,
        val with: Int,
        val height: Int
)

/**
 * Data class hold an Giphy image which includes a still image and a gif image info.
 */
data class GiphyImage (
        val stillImage: Image,
        val gifImage: Image
)
