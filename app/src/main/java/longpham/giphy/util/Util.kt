package longpham.giphy.util

import android.util.Log
import longpham.giphy.BuildConfig

private const val TAG = "GiphyApplication"

object LogUtil {
    fun d(message: String, tag: String = TAG) = log { Log.d(tag, message) }

    fun i(message: String, tag: String = TAG) = log { Log.i(tag, message) }

    fun e(message: String, tag: String = TAG) = log { Log.e(tag, message) }

    internal fun log(block: () -> Unit) {
        if (BuildConfig.DEBUG) {
            block.invoke()
        }
    }
}

fun Throwable.logException() = LogUtil.log { this.printStackTrace() }
