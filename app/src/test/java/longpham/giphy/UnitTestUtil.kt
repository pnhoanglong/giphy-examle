/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package longpham.giphy

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import longpham.giphy.models.GiphyImagesObject
import longpham.giphy.ui.image.RandomImageViewModel
import longpham.giphy.ui.trending.TrendingViewModel
import org.junit.Rule
import org.mockito.Mockito

open class BaseViewModelTestSuite {
    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()
}

/**
 * a kotlin friendly mock that handles generics
 */
inline fun <reified T> mock(): T = Mockito.mock(T::class.java)


fun TrendingViewModel.createTrendingImagesObserver() =
        mock<Observer<List<GiphyImagesObject>>>().also {
            images.observeForever(it)
        }

fun RandomImageViewModel.createSelectedImageObserver() =
        mock<Observer<GiphyImagesObject>>().also {
            randomImageLiveData.observeForever(it)
        }

