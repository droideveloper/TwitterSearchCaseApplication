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

import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import org.fs.mvp.common.AbstractPresenter
import org.fs.mvp.common.BusManager
import org.fs.twitter.model.Tweet
import org.fs.twitter.model.event.ShowTweetDetail
import org.fs.twitter.view.TweetDetailFragmentView

class TweetDetailFragmentPresenterImp(view: TweetDetailFragmentView)
  : AbstractPresenter<TweetDetailFragmentView>(view), TweetDetailFragmentPresenter {

  companion object {
    const val BUNDLE_ARGS_TWEET = "bundle.args.tweet"
  }

  private val disposeBag = CompositeDisposable()

  private var tweet: Tweet? = null

  override fun restoreState(restore: Bundle?) {
    restore?.let {
      if (it.containsKey(BUNDLE_ARGS_TWEET)) {
        tweet = it.getParcelable(BUNDLE_ARGS_TWEET)
      }
    }
  }

  override fun storeState(store: Bundle?) {
    store?.let { bag ->
      tweet?.let {
        bag.putParcelable(BUNDLE_ARGS_TWEET, it)
      }
    }
  }

  override fun onStart() {
    if (view.isAvailable()) {

      tweet?.let {
        view.showDetail(it)
      }

      val disposable = BusManager.add(Consumer { evt ->
        if (evt is ShowTweetDetail) {
          if (view.isAvailable()) {
            view.showDetail(evt.tweet)
          }
        }
      })

      disposeBag.add(disposable)
    }
  }

  override fun onStop() {
    disposeBag.clear()
  }
}  