/*
 * Copyright (C) 2016 Piotr Wittchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package longpham.giphy.ui.common

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * InfiniteScrollListener, which can be added to RecyclerView with addOnScrollListener
 * to detect moment when RecyclerView was scrolled to the end.
 */
abstract class InfiniteScrollListener
/**
 * Initializes InfiniteScrollListener, which can be added
 * to RecyclerView with addOnScrollListener method
 *
 * @param maxItemsPerRequest Max items to be loaded in a single request.
 * @param layoutManager LinearLayoutManager created in the Activity.
 */
(private val maxItemsPerRequest: Int, private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    init {
        Preconditions.checkIfPositive(maxItemsPerRequest, "maxItemsPerRequest <= 0")
        Preconditions.checkNotNull(layoutManager, "layoutManager == null")
    }

    /**
     * Callback method to be invoked when the RecyclerView has been scrolled
     *
     * @param recyclerView The RecyclerView which scrolled.
     * @param dx The amount of horizontal scroll.
     * @param dy The amount of vertical scroll.
     */
    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (canLoadMoreItems()) {
            onScrolledToEnd(layoutManager.findFirstVisibleItemPosition())
        }
    }

    /**
     * Refreshes RecyclerView by setting new adapter,
     * calling invalidate method and scrolling to given position
     *
     * @param view RecyclerView to be refreshed
     * @param position position to which RecyclerView will be scrolled
     */
    protected fun refreshView(view: RecyclerView, position: Int) {
        view.invalidate()
        view.scrollToPosition(position)
    }

    /**
     * Checks if more items can be loaded to the RecyclerView
     *
     * @return boolean Returns true if can load more items or false if not.
     */
    protected fun canLoadMoreItems(): Boolean {
        val visibleItemsCount = layoutManager.childCount
        val totalItemsCount = layoutManager.itemCount
        val pastVisibleItemsCount = layoutManager.findFirstVisibleItemPosition()
        val lastItemShown = visibleItemsCount + pastVisibleItemsCount >= totalItemsCount
        return lastItemShown && totalItemsCount >= maxItemsPerRequest
    }

    /**
     * Callback method to be invoked when the RecyclerView has been scrolled to the end
     *
     * @param firstVisibleItemPosition Id of the first visible item on the list.
     */
    abstract fun onScrolledToEnd(firstVisibleItemPosition: Int)
}

object Preconditions {
    fun checkNotNull(`object`: Any?, message: String) {
        if (`object` == null) {
            throw IllegalArgumentException(message)
        }
    }

    fun checkIfPositive(number: Int, message: String) {
        if (number <= 0) {
            throw IllegalArgumentException(message)
        }
    }
}