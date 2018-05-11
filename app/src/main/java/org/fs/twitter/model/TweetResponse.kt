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
package org.fs.twitter.model

import android.os.Parcel
import android.os.Parcelable
import org.fs.mvp.core.AbstractEntity

open class TweetResponse private constructor() : AbstractEntity() {

  var statuses: List<Tweet>? = null

  override fun readParcel(input: Parcel?) {
    val hasStatuses = input?.readInt() == 1
    if (hasStatuses) {
      statuses = ArrayList()
      input?.readTypedList(statuses, Tweet.CREATOR)
    }
  }

  override fun writeParcel(out: Parcel?, flags: Int) {
    val hasStatuses = statuses?.isEmpty() == false
    out?.writeInt(if (hasStatuses) 1 else 0)
    statuses?.let {
      out?.writeTypedList(statuses)
    }
  }

  companion object {
    @JvmField val CREATOR = object : Parcelable.Creator<TweetResponse> {

      override fun createFromParcel(source: Parcel?): TweetResponse {
        val obj = TweetResponse()
        obj.readParcel(source)
        return obj
      }

      override fun newArray(size: Int): Array<TweetResponse?> = arrayOfNulls(size)

    }
  }
}