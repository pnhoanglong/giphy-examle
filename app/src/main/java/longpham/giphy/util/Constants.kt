package longpham.giphy.util

import com.giphy.sdk.core.models.enums.MediaType

object AppConstants {
    const val ITEMS_PER_REQUEST = 15
    const val RANDOM_IMAGE_INTERVAL = 10000L
    const val MAX_IMAGE_HEIGHT = 2000
}

object GiphyConstants {
    const val API_KEY = "dc6zaTOxFJmzC"
    val IMAGE_TYPE = MediaType.gif
    val RATING = null
    const val DEFAULT_IMAGE_TAG = ""
}
