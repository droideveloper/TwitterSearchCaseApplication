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

open class User private constructor() : AbstractEntity() {

  var id: Long? = null
  @SerializedName("screen_name") var screenName: String? = null
  var name: String? = null

  override fun readParcel(input: Parcel?) {
    val hasId = input?.readInt() == 1
    if (hasId) {
      id = input?.readLong()
    }
    val hasScreenName = input?.readInt() == 1
    if (hasScreenName) {
      screenName = input?.readString()
    }
    val hasName = input?.readInt() == 1
    if (hasName) {
      name = input?.readString()
    }
  }

  override fun writeParcel(out: Parcel?, flags: Int) {
    val hasId = id != null
    out?.writeInt(if (hasId) 1 else 0)
    id?.let {
      out?.writeLong(it)
    }
    val hasScreenName = !TextUtils.isEmpty(screenName)
    out?.writeInt(if (hasScreenName) 1 else 0)
    screenName?.let {
      out?.writeString(it)
    }
    val hasName = !TextUtils.isEmpty(name)
    out?.writeInt(if (hasName) 1 else 0)
    name?.let {
      out?.writeString(it)
    }
  }

  companion object {
    @JvmField val CREATOR = object : Parcelable.Creator<User> {

      override fun createFromParcel(source: Parcel?): User {
        val obj = User()
        obj.readParcel(source)
        return obj
      }

      override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)

    }
  }
}