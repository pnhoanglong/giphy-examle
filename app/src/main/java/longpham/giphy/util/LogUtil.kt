package longpham.giphy.util

import android.util.Log
import longpham.giphy.BuildConfig

object LogUtil {
    fun d(tag: String, message: String)  = log { Log.d(tag, message) }

    fun i(tag: String, message: String)  = log { Log.i(tag, message) }

    fun e(tag: String, message: String) = log { Log.e(tag, message) }

    internal fun log(block: () -> Unit) {
        if (BuildConfig.DEBUG) {
            block.invoke()
        }
    }
}

fun Throwable.logException() = LogUtil.log { this.printStackTrace() }
