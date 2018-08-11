package longpham.giphy.util

import longpham.giphy.repository.IRepository
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

fun showRandomImage(repository: IRepository) {
    val tag = "RandomImage"
    LogUtil.i("Execute get random image")
    repository.getRandomImage("tag").observeForever {
        LogUtil.i(tag, it.toString())
    }
}