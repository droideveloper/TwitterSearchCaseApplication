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

import android.os.Handler
import android.os.Looper

class ThreadManager private constructor() {

  companion object {
    private val uiHandler = Handler(Looper.myLooper())
    private val DELAY_MS = 300L

    fun runOnUiThread(task: Runnable) = uiHandler.post(task)
    fun runOnUiThreadDelayed(task: Runnable, delay: Long = DELAY_MS) = uiHandler.postDelayed(task, delay)
    fun clearAll() = uiHandler.removeCallbacksAndMessages(null)
    fun clear(task: Runnable) = uiHandler.removeCallbacks(task)
  }
}