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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.view_imaged_tweet_item.view.*
import org.fs.mvp.common.BusManager
import org.fs.twitter.R
import org.fs.twitter.common.BaseTweetViewHolder
import org.fs.twitter.model.Tweet
import org.fs.twitter.model.event.SelectedTweetEvent
import org.fs.uibinding.util.clicks

class ImagedTweetViewHolder(view: View) : BaseTweetViewHolder(view) {

  companion object {
    private const val TYPE_PHOTO = "photo"
  }

  private val disposeBag = CompositeDisposable()

  override fun onBindView(entity: Tweet?) {
    super.onBindView(entity)
    entity?.let {
      itemView.viewTweetTitle.text = it.text
      itemView.viewTweetTime.text = it.createdAt
      // load first image we grab for show
      it.entities?.let { e ->
        val first = e.media?.first { t -> TextUtils.equals(TYPE_PHOTO, t.type)}
        first?.let { img ->
            Glide.with(itemView.context)
              .load(img.imageUrl)
              .fitCenter()
              .placeholder(R.drawable.list_item_decorator)
              .diskCacheStrategy(DiskCacheStrategy.SOURCE)
              .into(itemView.viewTweetImage)

        }
      }
    }
  }

  override fun attached() {
    super.attached()
    val disposable = itemView.clicks()
      .subscribe {
        entity?.let {
          BusManager.send(SelectedTweetEvent(it))
        }
      }

    disposeBag.add(disposable)
  }

  override fun detached() {
    disposeBag.clear()
    super.detached()
  }
}