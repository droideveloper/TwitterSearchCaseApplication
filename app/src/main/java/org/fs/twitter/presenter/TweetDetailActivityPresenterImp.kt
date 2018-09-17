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
import org.fs.architecture.common.AbstractPresenter
import org.fs.architecture.common.scope.ForActivity
import org.fs.twitter.model.Tweet
import org.fs.twitter.util.plusAssign
import org.fs.twitter.view.TweetDetailActivityView
import javax.inject.Inject

@ForActivity
class TweetDetailActivityPresenterImp @Inject constructor(view: TweetDetailActivityView): AbstractPresenter<TweetDetailActivityView>(view), TweetDetailActivityPresenter {

  companion object {
    const val BUNDLE_ARGS_TWEET = "bundle.args.tweet"
  }

  private val disposeBag by lazy { CompositeDisposable() }

  private var tweet: Tweet? = null

  override fun restoreState(restore: Bundle?) {
    restore?.let {
      if(it.containsKey(BUNDLE_ARGS_TWEET)) {
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

  override fun onCreate() {
    if (view.isAvailable()) {
      tweet?.let {
        view.replace(it)
      }
    }
  }

  override fun onStart() {
    if (view.isAvailable()) {
      disposeBag += view.navigationClick()
        .subscribe { _-> onBackPressed() }
    }
  }

  override fun onStop() = disposeBag.clear()

  override fun onBackPressed() {
    if (view.isAvailable()) {
      view.finish()
    }
  }
}  