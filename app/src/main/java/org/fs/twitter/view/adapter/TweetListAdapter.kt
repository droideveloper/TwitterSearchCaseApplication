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
package org.fs.twitter.view.adapter

import android.view.ViewGroup
import org.fs.mvp.core.AbstractRecyclerViewAdapter
import org.fs.mvp.util.ObservableList
import org.fs.twitter.R
import org.fs.twitter.common.BaseTweetViewHolder
import org.fs.twitter.model.Tweet
import org.fs.twitter.view.holder.ImagedTweetViewHolder
import org.fs.twitter.view.holder.SimpleTweetViewHolder
import org.fs.twitter.view.holder.TweetLoadMoreViewHolder

class TweetListAdapter(dataSet: ObservableList<Tweet>) : AbstractRecyclerViewAdapter<Tweet, BaseTweetViewHolder>(dataSet) {

  companion object {
    private const val VIEW_TYPE_SIMPLE_TWEET = 0x01
    private const val VIEW_TYPE_IMAGED_TWEET = 0x02
    private const val VIEW_TYPE_PROGRESS = 0x03
  }

  override fun onViewAttachedToWindow(viewHolder: BaseTweetViewHolder) {
    super.onViewAttachedToWindow(viewHolder)
    viewHolder.attached()
  }

  override fun onViewDetachedFromWindow(viewHolder: BaseTweetViewHolder) {
    viewHolder.detached()
    super.onViewDetachedFromWindow(viewHolder)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseTweetViewHolder {
    val factory = factory(parent)
    factory?.let {
      return when (viewType) {
        VIEW_TYPE_PROGRESS -> TweetLoadMoreViewHolder(factory.inflate(R.layout.view_progress_item, parent, false))
        VIEW_TYPE_SIMPLE_TWEET -> SimpleTweetViewHolder(factory.inflate(R.layout.view_simple_tweet_item, parent, false))
        VIEW_TYPE_IMAGED_TWEET -> ImagedTweetViewHolder(factory.inflate(R.layout.view_imaged_tweet_item, parent, false))
        else -> throw RuntimeException("can not identify view type $viewType")
      }
    }
    throw RuntimeException("can not identify view type $viewType")
  }

  override fun getItemViewType(position: Int): Int {
    val item = itemAt(position)
    return when {
      item == Tweet.EMPTY -> VIEW_TYPE_PROGRESS
      item.entities?.media == null -> VIEW_TYPE_SIMPLE_TWEET
      else -> VIEW_TYPE_IMAGED_TWEET
    }
  }
} 