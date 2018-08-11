package longpham.giphy.util

import com.giphy.sdk.core.models.enums.MediaType

object AppConstants {
    const val INIT_LOAD_ITEMS_COUNT = 5
    const val LOAD_MORE_ITEMS_COUNT = 15
    const val RANDOM_IMAGE_INTERVAL = 10000L
}

object GiphyConstants {
    const val API_KEY = "dc6zaTOxFJmzC"
    val IMAGE_TYPE = MediaType.gif
    val RATING = null
    const val DEFAULT_IMAGE_TAG = "android"
}
