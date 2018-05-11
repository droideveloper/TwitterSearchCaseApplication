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

open class Tweet private constructor() : AbstractEntity() {

  @SerializedName("created_at")var createdAt: String? = null
  var id: Long? = null
  var text: String? = null
  var user: User? = null
  var entities: Entity? = null
  @SerializedName("extended_entities") var extendedEntities: Entity? = null

  override fun readParcel(input: Parcel?) {
    val hasCreatedAt = input?.readInt() == 1
    if (hasCreatedAt) {
      createdAt = input?.readString()
    }
    val hasId = input?.readInt() == 1
    if (hasId) {
      id = input?.readLong()
    }
    val hasUser = input?.readInt() == 1
    if (hasUser) {
      user = input?.readParcelable(User::class.java.classLoader)
    }
    val hasEntities = input?.readInt() == 1
    if (hasEntities) {
      entities = input?.readParcelable(Entity::class.java.classLoader)
    }
    val hasExtendedEntities = input?.readInt() == 1
    if (hasExtendedEntities) {
      extendedEntities = input?.readParcelable(Entity::class.java.classLoader)
    }
  }

  override fun writeParcel(out: Parcel?, flags: Int) {
    val hasCreatedAt = !TextUtils.isEmpty(createdAt)
    out?.writeInt(if (hasCreatedAt) 1 else 0)
    createdAt?.let {
      out?.writeString(it)
    }
    val hasId = id != null
    out?.writeInt(if (hasId) 1 else 0)
    id?.let {
      out?.writeLong(it)
    }
    val hasUser = user != null
    out?.writeInt(if (hasUser) 1 else 0)
    user?.let {
      out?.writeParcelable(it, flags)
    }
    val hasEntities = entities != null
    out?.writeInt(if (hasEntities) 1 else 0)
    entities?.let {
      out?.writeParcelable(it, flags)
    }
    val hasExtendedEntities = extendedEntities != null
    out?.writeInt(if (hasExtendedEntities) 1 else 0)
    extendedEntities?.let {
      out?.writeParcelable(it, flags)
    }
  }

  companion object {
    val EMPTY = Tweet()

    @JvmField val CREATOR = object : Parcelable.Creator<Tweet> {

      override fun createFromParcel(source: Parcel?): Tweet {
        val obj = Tweet()
        obj.readParcel(source)
        return obj
      }

      override fun newArray(size: Int): Array<Tweet?> = arrayOfNulls(size)
    }
  }
}