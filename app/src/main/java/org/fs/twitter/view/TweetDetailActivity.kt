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
package org.fs.twitter.view

import android.os.Bundle
import android.view.View
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_tweet_detail_activity.*
import org.fs.architecture.core.AbstractActivity
import org.fs.rx.extensions.v7.util.navigationClicks
import org.fs.twitter.R
import org.fs.twitter.model.Tweet
import org.fs.twitter.presenter.TweetDetailActivityPresenter

class TweetDetailActivity : AbstractActivity<TweetDetailActivityPresenter>(), TweetDetailActivityView {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.view_tweet_detail_activity)

    presenter.restoreState(savedInstanceState ?: intent.extras)
    presenter.onCreate()
  }

  override fun replace(tweet: Tweet) {
    val fragment = TweetDetailFragment.create(tweet)
    supportFragmentManager.beginTransaction()
      .replace(R.id.viewContentFrameLayout, fragment)
      .commit()
  }

  override fun navigationClick(): Observable<View> = viewToolbar.navigationClicks()
}