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
package org.fs.uibinding.v7.observable

import android.os.Looper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.atomic.AtomicBoolean


class RecyclerViewScrollObservable(val view: RecyclerView) : Observable<Boolean>() {

  override fun subscribeActual(observer: Observer<in Boolean>?) {
    observer?.let {
      val listener = Listener(view, it)
      it.onSubscribe(listener)
      view.addOnScrollListener(listener)
    }
  }

  class Listener(val view: RecyclerView, val observer: Observer<in Boolean>): RecyclerView.OnScrollListener(), Disposable {

    var firstVisibleItem: Int = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0
    var visibleThreshold = 0
    private var previousTotal = 0
    private var loading = true

    var target: Int = 0
    var visibleItems: Int = 0

    private val unsubscribed = AtomicBoolean()

    override fun isDisposed(): Boolean {
      return unsubscribed.get()
    }

    override fun dispose() {
      if (unsubscribed.compareAndSet(false, true)) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
          onDispose()
        } else {
          AndroidSchedulers.mainThread().scheduleDirect { this.onDispose() }
        }
      }
    }

    fun onDispose() {
      view.removeOnScrollListener(this)
    }

    private val layoutManager: RecyclerView.LayoutManager = view.layoutManager

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
      if (dy <= 0) return

      visibleItemCount = view.childCount
      totalItemCount = layoutManager.itemCount
      firstVisibleItem = firstVisibleItemPosition()

      if (loading) {
        if (totalItemCount != previousTotal) {
          loading = false
          previousTotal = totalItemCount
        }
      }

      val c = totalItemCount / 3
      if (target == 0) {
        target = c
        visibleItems = visibleItemCount
      }
      if (firstVisibleItem >= target && totalItemCount > 0) {

        if (target == c * 2) {
          target = totalItemCount - visibleItems
        } else {
          target += c
          if (!isDisposed) {
            observer.onNext(loading)
          }
        }
      }

      if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
        // End has been reached invoke the callback.
        if (!isDisposed) {
          observer.onNext(false)
        }
        loading = true
      }
    }

    private fun firstVisibleItemPosition(): Int = when (layoutManager) {
      is LinearLayoutManager -> layoutManager.findFirstVisibleItemPosition()
      is StaggeredGridLayoutManager -> {
        val array = layoutManager.findFirstVisibleItemPositions(null)
        if (array.isEmpty()) 0 else array.first()
      }
      else -> 0
    }
  }
}