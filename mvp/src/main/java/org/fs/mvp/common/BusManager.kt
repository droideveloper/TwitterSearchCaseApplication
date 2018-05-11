/*
 * MVP Android Kotlin Copyright (C) 2017 Fatih, Brokoli Labs.
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
package org.fs.mvp.common

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

class BusManager private constructor() {

  companion object {
    private val imp = BusManager()
    private val rxBus = PublishSubject.create<EventType>()

    fun <T: EventType> send(event: T) = imp.post(event)
    fun add(observer: Consumer<EventType>): Disposable = imp.register(observer)
    fun remove(disposable: Disposable) = imp.unregister(disposable)
  }

  private fun <T: EventType> post(event: T) = rxBus.onNext(event)
  private fun register(observer: Consumer<EventType>): Disposable = rxBus.subscribe(observer)
  private fun unregister(disposable: Disposable) {
    if (!disposable.isDisposed) {
      disposable.dispose()
    }
  }
}