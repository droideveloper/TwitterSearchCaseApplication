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

import android.view.View
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.view_simple_tweet_item.view.*
import org.fs.mvp.common.BusManager
import org.fs.twitter.common.BaseTweetViewHolder
import org.fs.twitter.model.Tweet
import org.fs.twitter.model.event.SelectedTweetEvent
import org.fs.uibinding.util.clicks

class SimpleTweetViewHolder(view: View) : BaseTweetViewHolder(view) {

  private val disposeBag = CompositeDisposable()

  override fun onBindView(entity: Tweet?) {
    super.onBindView(entity)
    entity?.let {
      itemView.viewTweetTitle.text = it.text
      itemView.viewTweetTime.text = it.createdAt
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