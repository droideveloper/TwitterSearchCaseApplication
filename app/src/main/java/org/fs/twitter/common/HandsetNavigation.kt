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
package org.fs.twitter.common

import android.app.Activity
import android.content.Intent
import org.fs.architecture.common.NavigationType
import org.fs.twitter.model.Tweet
import org.fs.twitter.presenter.TweetDetailActivityPresenterImp
import org.fs.twitter.view.TweetDetailActivity

class HandsetNavigation(private val activity: Activity): NavigationType<Tweet> {

  companion object {
    const val REQUEST_CODE_DETAIL = 0x09
  }

  override fun onSelectCategory(tweet: Tweet?) = activity.startActivityForResult(Intent(activity, TweetDetailActivity::class.java).apply {
    putExtra(TweetDetailActivityPresenterImp.BUNDLE_ARGS_TWEET, tweet)
  }, REQUEST_CODE_DETAIL)
}