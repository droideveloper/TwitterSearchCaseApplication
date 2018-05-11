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

import android.content.Intent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import org.fs.mvp.common.AbstractPresenter
import org.fs.mvp.common.BusManager
import org.fs.mvp.common.NavigationType
import org.fs.mvp.common.ThreadManager
import org.fs.twitter.model.Tweet
import org.fs.twitter.model.event.RefreshSearchListEvent
import org.fs.twitter.model.event.SelectedTweetEvent
import org.fs.twitter.view.MainActivityView

class MainActivityPresenterImp(view: MainActivityView,
    val navigationType: NavigationType<Tweet>) :
    AbstractPresenter<MainActivityView>(view), MainActivityPresenter {

  companion object {
    private const val DELAY_FOR_FRAGMENT_GET_AWAKE = 300L
  }

  private val disposeBag = CompositeDisposable()

  override fun onStart() {
    if (view.isAvailable()) {
      val disposable = BusManager.add(Consumer { evt ->
        if (evt is SelectedTweetEvent) {
          if (view.isAvailable()) {
            navigationType.onSelectCategory(evt.tweet)
          }
        }
      })

      disposeBag.add(disposable)
    }
  }

  override fun onStop() {
    disposeBag.clear()
  }

  override fun onBackPressed() {
    if (view.isAvailable()) {
      view.finish()
    }
  }

  override fun activityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    ThreadManager.runOnUiThreadDelayed(Runnable {
      BusManager.send(RefreshSearchListEvent())
    }, DELAY_FOR_FRAGMENT_GET_AWAKE)
  }
}  