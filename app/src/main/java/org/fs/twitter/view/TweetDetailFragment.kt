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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.view_tweet_detail_fragment.*
import org.fs.architecture.core.AbstractFragment
import org.fs.architecture.util.inflate
import org.fs.twitter.R
import org.fs.twitter.common.GlideApp
import org.fs.twitter.model.Tweet
import org.fs.twitter.presenter.TweetDetailFragmentPresenter
import org.fs.twitter.presenter.TweetDetailFragmentPresenterImp

class TweetDetailFragment : AbstractFragment<TweetDetailFragmentPresenter>(), TweetDetailFragmentView {

  companion object {
    fun create(tweet: Tweet): TweetDetailFragment {
      val args = Bundle()
      args.putParcelable(TweetDetailFragmentPresenterImp.BUNDLE_ARGS_TWEET, tweet)
      val fragment = TweetDetailFragment()
      fragment.arguments = args
      return fragment
    }
  }

  private val glide by lazy { GlideApp.with(this) }

  override fun onCreateView(factory: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? = parent?.inflate(R.layout.view_tweet_detail_fragment)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    presenter.restoreState(savedInstanceState ?: arguments)
    presenter.onCreate()
  }

  override fun showDetail(tweet: Tweet) {
    viewTweetTitle.text = tweet.text
    viewTweetTime.text = tweet.createdAt
    val img = tweet.entities?.media?.last()
    img?.let { media ->
      glide.load(media.imageUrl)
        .centerCrop()
        .placeholder(R.drawable.list_item_decorator)
        .into(viewTweetImage)
    }
    if (img == null) {
      glide.clear(viewTweetImage)
    }
  }

  override fun clearDetail() {
    viewTweetTitle.text = null
    viewTweetTime.text = null
    glide.clear(viewTweetImage)
  }
}
