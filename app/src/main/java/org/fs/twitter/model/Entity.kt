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
import com.google.gson.annotations.SerializedName
import org.fs.mvp.core.AbstractEntity

open class Entity private constructor() : AbstractEntity() {

  var media: List<Media>? = null
  @SerializedName("user_mentions") var userMentions: List<User>? = null

  override fun readParcel(input: Parcel?) {
    val hasMedia = input?.readInt() == 1
    if (hasMedia) {
      media = ArrayList()
      input?.readTypedList(media, Media.CREATOR)
    }
    val hasUserMentions = input?.readInt() == 1
    if (hasUserMentions) {
      userMentions = ArrayList()
      input?.readTypedList(userMentions, User.CREATOR)
    }
  }

  override fun writeParcel(out: Parcel?, flags: Int) {
    val hasMedia = media != null && media?.isEmpty() == false
    out?.writeInt(if (hasMedia) 1 else 0)
    media?.let {
      out?.writeTypedList(it)
    }
    val hasUserMentions = userMentions != null && userMentions?.isEmpty() == false
    out?.writeInt(if (hasUserMentions) 1 else 0)
    userMentions?.let {
      out?.writeTypedList(it)
    }
  }

  companion object {
    @JvmField val CREATOR = object : Parcelable.Creator<Entity> {

      override fun createFromParcel(source: Parcel?): Entity {
        val obj = Entity()
        obj.readParcel(source)
        return obj
      }

      override fun newArray(size: Int): Array<Entity?> = arrayOfNulls(size)
    }
  }
}