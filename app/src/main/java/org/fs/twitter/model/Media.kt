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
import android.text.TextUtils
import com.google.gson.annotations.SerializedName
import org.fs.mvp.core.AbstractEntity

open class Media private constructor() : AbstractEntity() {

  var id: Int? = null
  @SerializedName("media_url") var imageUrl: String? = null
  var type: String? = null

  override fun readParcel(input: Parcel?) {
    val hasId = input?.readInt() == 1
    if (hasId) {
      id = input?.readInt()
    }
    val hasImageUrl = input?.readInt() == 1
    if (hasImageUrl) {
      imageUrl = input?.readString()
    }
    val hasType = input?.readInt() == 1
    if (hasType) {
      type = input?.readString()
    }
  }

  override fun writeParcel(out: Parcel?, flags: Int) {
    val hasId = id != null
    out?.writeInt(if (hasId) 1 else 0)
    id?.let {
      out?.writeInt(it)
    }
    val hasImageUrl = !TextUtils.isEmpty(imageUrl)
    out?.writeInt(if (hasImageUrl) 1 else 0)
    imageUrl?.let {
      out?.writeString(it)
    }
    val hasType = !TextUtils.isEmpty(type)
    out?.writeInt(if (hasType) 1 else 0)
    type?.let {
      out?.writeString(it)
    }
  }

  companion object {

    @JvmField val CREATOR = object : Parcelable.Creator<Media> {

      override fun createFromParcel(source: Parcel?): Media {
        val obj = Media()
        obj.readParcel(source)
        return obj
      }

      override fun newArray(size: Int): Array<Media?> = arrayOfNulls(size)
    }
  }
}