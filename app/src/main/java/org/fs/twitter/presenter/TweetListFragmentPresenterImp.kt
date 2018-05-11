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
import org.fs.mvp.common.AbstractPresenter
import org.fs.mvp.common.BusManager
import org.fs.mvp.util.ObservableList
import org.fs.twitter.model.Authorization
import org.fs.twitter.model.Tweet
import org.fs.twitter.net.Endpoint
import org.fs.twitter.util.EMPTY
import org.fs.twitter.util.async
import org.fs.twitter.view.TweetListFragmentView

class TweetListFragmentPresenterImp(view: TweetListFragmentView,
    private val endpoint: Endpoint,
    private val dataSet: ObservableList<Tweet>) :
    AbstractPresenter<TweetListFragmentView>(view), TweetListFragmentPresenter {

  companion object {
    private const val MIN_CHAR_COUNT = 3
  }

  private val disposeBag = CompositeDisposable()

  override fun onCreate() {
    if (view.isAvailable()) {
      view.setUp()
    }
  }

  override fun onStart() {
    if (view.isAvailable()) {
      val disposable = BusManager.add(Consumer { evt ->

      })

      disposeBag.add(disposable)

      val observeQueryChanges = view.queryChanges()
        .map { it.trim() }
        .filter { it.length >= MIN_CHAR_COUNT }
        .flatMap { endpoint.search(Authorization.create(it.toString()), it.toString()) }
        .map { it.statuses ?: emptyList() }
        .async(view)
        .doOnSubscribe {
          if (view.isAvailable()) {
            dataSet.clear()
          }
        }
        .subscribe({ array ->
          if (view.isAvailable()) {
            dataSet.addAll(array)
          }
        }, { error -> error.printStackTrace() })

      disposeBag.add(observeQueryChanges)

      val observeLoadMore = view.loadMore()
        .filter { it }
        .map { dataSet.last().id?.toString() ?: String.EMPTY }
        .flatMap { endpoint.loadMore(Authorization.create(view.query(), it), view.query(), it) }
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

      disposeBag.add(observeLoadMore)
    }
  }

  override fun onStop() {
    disposeBag.clear()
  }
}  