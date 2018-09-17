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

package org.fs.twitter.util

import android.util.Base64
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.fs.architecture.common.ViewType
import java.util.*
import java.util.concurrent.TimeUnit
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

// algorithms used to sign (only name)
const val HMAC_SHA1 = "HmacSHA1"

const val TWITTER_ENDPOINT = "https://api.twitter.com/1.1/search/tweets.json"
const val TWITTER_HOST = "api.twitter.com"
const val TWITTER_PATH = "/1.1/search/tweets.json"

// Empty String representations
val String.Companion.EMPTY
  get() = ""

// create signature for needed string and key
fun String.computeSignature(keyString: String, algorithm: String = HMAC_SHA1): String {

  val bytes = keyString.toByteArray()
  val secretKey = SecretKeySpec(bytes, algorithm)

  val mac = Mac.getInstance(algorithm)
  mac.init(secretKey)

  val text = toByteArray()
  return String(Base64.encode(mac.doFinal(text), Base64.DEFAULT)).trim()
}

// nonce create uniquely
fun newNonce(): String = UUID.randomUUID()
  .toString()
  .replace("-", String.EMPTY)

// current time as seconds
fun currentTime(): String  {
  val calendar = Calendar.getInstance(TimeZone.getDefault())
  return TimeUnit.MILLISECONDS
    .toSeconds(calendar.timeInMillis)
    .toString()
}

// Rx Extensions

// Observable
fun <T> Observable<T>.async(): Observable<T> = this.subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.async(view: ViewType): Observable<T> = this.async()
  .doOnSubscribe {
    if (view.isAvailable()) {
      view.showProgress()
    }
  }
  .doFinally {
    if (view.isAvailable()) {
      view.hideProgress()
    }
  }

// Single
fun <T> Single<T>.async(): Single<T> = this.subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.async(view: ViewType) = this.async()
  .doOnSubscribe {
    if (view.isAvailable()) {
      view.showProgress()
    }
  }
  .doFinally {
    if (view.isAvailable()) {
      view.hideProgress()
    }
  }

// Maybe
fun <T> Maybe<T>.async(): Maybe<T> = this.subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.async(view: ViewType) = this.async()
  .doOnSubscribe {
    if (view.isAvailable()) {
      view.showProgress()
    }
  }
  .doFinally {
    if (view.isAvailable()) {
      view.hideProgress()
    }
  }

// Completable
fun Completable.async(): Completable = this.subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())

fun Completable.async(view: ViewType) = this.async()
  .doOnSubscribe {
    if (view.isAvailable()) {
      view.showProgress()
    }
  }
  .doFinally {
    if (view.isAvailable()) {
      view.hideProgress()
    }
  }

operator fun CompositeDisposable.plusAssign(d: Disposable) {
  add(d)
}
