package longpham.giphy.util

import longpham.giphy.repository.IRepository

fun showRandomImage(repository: IRepository){
    val tag = "RandomImage"
    LogUtil.i(tag, "Execute get random image")
    repository.getRandomImage("tag").observeForever {
        LogUtil.i(tag, it.toString())
    }
}