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
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_progress_item.view.*
import org.fs.architecture.util.inflate
import org.fs.twitter.R
import org.fs.twitter.common.BaseTweetViewHolder
import org.fs.twitter.model.Tweet

class TweetLoadMoreViewHolder(view: View) : BaseTweetViewHolder(view) {

  constructor(parent: ViewGroup): this(parent.inflate(R.layout.view_progress_item))

  override fun onBindView(entity: Tweet?) = Unit

  override fun attached() {
    itemView.viewProgress.isIndeterminate = true
  }

  override fun detached() {
    itemView.viewProgress.isIndeterminate = false
  }
}