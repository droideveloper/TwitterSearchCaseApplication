/*
 * UIBinding Android Kotlin Copyright (C) 2017 Fatih, Brokoli Labs.
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
package org.fs.uibinding.observable

import android.view.View
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import org.fs.uibinding.util.checkMainThread

class ViewLongClickObservable(private val view: View, private val predicate: () -> Boolean): Observable<View>() {

  override fun subscribeActual(observer: Observer<in View>?) {
    if (observer != null) {
      if (!observer.checkMainThread()) { return }

      val listener = Listener(view, observer, predicate)
      observer.onSubscribe(listener)
      view.setOnLongClickListener(listener)
    }
  }

  class Listener(private val view: View, private val observer: Observer<in View>, private val predicate: () -> Boolean): MainThreadDisposable(), View.OnLongClickListener {

    override fun onDispose() {
      view.setOnLongClickListener(null)
    }

    override fun onLongClick(view: View?): Boolean {
      if (!isDisposed) {
        observer.onNext(this.view)
        return predicate()
      }
      return false
    }
  }
}