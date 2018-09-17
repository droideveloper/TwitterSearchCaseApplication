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

import android.view.View
import org.fs.architecture.core.AbstractRecyclerViewHolder
import org.fs.twitter.model.Tweet

abstract class BaseTweetViewHolder(view: View) : AbstractRecyclerViewHolder<Tweet>(view) {

  override fun onBindView(entity: Tweet?) {
   this.entity = entity
  }
}