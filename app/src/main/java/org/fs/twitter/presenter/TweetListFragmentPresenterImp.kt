/*
 * Twitter Search Case Application Copyright (C) 2018 Fatih.
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
package org.fs.twitter.presenter

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import org.fs.architecture.common.AbstractPresenter
import org.fs.architecture.common.BusManager
import org.fs.architecture.common.scope.ForFragment
import org.fs.architecture.util.ObservableList
import org.fs.twitter.model.Authorization
import org.fs.twitter.model.Tweet
import org.fs.twitter.model.event.RefreshSearchListEvent
import org.fs.twitter.net.Endpoint
import org.fs.twitter.util.EMPTY
import org.fs.twitter.util.async
import org.fs.twitter.util.plusAssign
import org.fs.twitter.view.TweetListFragmentView
import javax.inject.Inject

@ForFragment
class TweetListFragmentPresenterImp @Inject constructor(view: TweetListFragmentView,
    private val endpoint: Endpoint,
    private val dataSet: ObservableList<Tweet>): AbstractPresenter<TweetListFragmentView>(view), TweetListFragmentPresenter {

  companion object {
    private const val MIN_CHAR_COUNT = 3
  }

  private val disposeBag by lazy {  CompositeDisposable() }

  private var query: String = String.EMPTY

  override fun onCreate() {
    if (view.isAvailable()) {
      view.setUp()
    }
  }

  override fun onStart() {
    if (view.isAvailable()) {
      disposeBag += BusManager.add(Consumer { evt ->
        if (evt is RefreshSearchListEvent) {
          if (view.isAvailable()) {
            dataSet.clear()
            load(query, false)
          }
        }
      })

     disposeBag += view.queryChanges()
      .map { it.trim() }
      .filter { it.length >= MIN_CHAR_COUNT }
      .subscribe({
        dataSet.clear()
        load(it.toString(), false)
      }, { error -> error.printStackTrace() })

      disposeBag += view.loadMore()
        .filter { it }
        .subscribe({ load(query, true) }, { error -> error.printStackTrace() })

      disposeBag += view.refreshes()
        .filter { it }
        .subscribe({
          dataSet.clear()
          load(query, false)
        }, { error -> error.printStackTrace() })
    }
  }

  override fun onStop() = disposeBag.clear()

  private fun load(query: String, loadMore: Boolean) {
    this.query = query
    if (loadMore) {
      val since = dataSet.last().id?.toString() ?: String.EMPTY
      disposeBag += endpoint.loadMore(Authorization.create(query, since), query, since)
        .map { it.statuses ?: emptyList() }
        .async()
        .doOnSubscribe {
          if (view.isAvailable()) {
            dataSet.add(Tweet.EMPTY) // add indicator twitter
          }
        }
        .subscribe({ array ->
          if (view.isAvailable()) {
            val index = dataSet.indexOfFirst { it == Tweet.EMPTY }
            if (index != -1) {
              dataSet.removeAt(index)
            }
            dataSet.addAll(array)
          }
        }, { error -> error.printStackTrace() })

    } else {
      disposeBag += endpoint.search(Authorization.create(query), query)
        .map { it.statuses ?: emptyList() }
        .async(view)
        .subscribe({ array ->
          if (view.isAvailable()) {
            dataSet.addAll(array)
          }
        }, { error -> error.printStackTrace() })
    }
  }
}  