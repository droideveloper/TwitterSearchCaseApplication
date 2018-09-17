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
package org.fs.twitter.view.holder

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.view_imaged_tweet_item.view.*
import org.fs.architecture.common.BusManager
import org.fs.architecture.util.inflate
import org.fs.rx.extensions.util.clicks
import org.fs.twitter.R
import org.fs.twitter.common.BaseTweetViewHolder
import org.fs.twitter.common.GlideApp
import org.fs.twitter.model.Tweet
import org.fs.twitter.model.event.SelectedTweetEvent
import org.fs.twitter.util.plusAssign

class ImagedTweetViewHolder(view: View) : BaseTweetViewHolder(view) {

  companion object {
    private const val TYPE_PHOTO = "photo"
  }

  private val disposeBag by lazy { CompositeDisposable() }
  private val glide by lazy { GlideApp.with(view) }

  constructor(parent: ViewGroup): this(parent.inflate(R.layout.view_imaged_tweet_item))

  override fun onBindView(entity: Tweet?) {
    super.onBindView(entity)
    entity?.let { tweet ->
      itemView.viewTweetTitle.text = tweet.text
      itemView.viewTweetTime.text = tweet.createdAt
      // load first image we grab for show
      tweet.entities?.let { e ->
        val first = e.media?.first { t -> TextUtils.equals(TYPE_PHOTO, t.type)}
        first?.let { img ->
            glide.load(img.imageUrl)
              .centerCrop()
              .into(itemView.viewTweetImage)

        }
      }
    }
  }

  override fun attached() {
    disposeBag += bindSelectedTweetEvent(entity).subscribe(BusManager.Companion::send)
  }

  override fun detached() = disposeBag.clear()

  private fun bindSelectedTweetEvent(tweet: Tweet?): Observable<SelectedTweetEvent> = itemView.clicks()
    .map { _ -> SelectedTweetEvent(tweet ?: Tweet.EMPTY) }
}