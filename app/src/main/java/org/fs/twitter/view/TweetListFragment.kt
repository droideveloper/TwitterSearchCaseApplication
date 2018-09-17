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
package org.fs.twitter.view

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_tweet_list_fragment.*
import org.fs.architecture.core.AbstractFragment
import org.fs.rx.extensions.v4.util.refreshes
import org.fs.rx.extensions.v7.util.loadMore
import org.fs.rx.extensions.v7.util.queryChanges
import org.fs.twitter.R
import org.fs.twitter.presenter.TweetListFragmentPresenter
import org.fs.twitter.view.adapter.TweetListAdapter
import javax.inject.Inject

class TweetListFragment : AbstractFragment<TweetListFragmentPresenter>(), TweetListFragmentView {

  @Inject lateinit var tweetListAdapter: TweetListAdapter

  override fun onCreateView(factory: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? = factory.inflate(R.layout.view_tweet_list_fragment, parent, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    presenter.restoreState(savedInstanceState ?: arguments)
    presenter.onCreate()
  }

  override fun setUp() {
    viewSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent)
    // set up recycler
    val drawable = ResourcesCompat.getDrawable(resources, R.drawable.list_item_decorator, context?.theme)
    if (drawable != null) {
      val listDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
      viewRecycler.addItemDecoration(listDecorator)
    }
    // set up other pieces
    viewRecycler.setHasFixedSize(true)
    viewRecycler.isDrawingCacheEnabled = true
    viewRecycler.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
    viewRecycler.adapter = tweetListAdapter
    viewRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
  }

  override fun showProgress() {
    viewSwipeRefreshLayout.isRefreshing = true
  }

  override fun hideProgress() {
    viewSwipeRefreshLayout.isRefreshing = false
  }

  override fun query(): String = viewSearch.query.toString().trim()
  override fun loadMore(): Observable<Boolean> = viewRecycler.loadMore()
  override fun refreshes(): Observable<Boolean> = viewSwipeRefreshLayout.refreshes()
  override fun queryChanges(): Observable<CharSequence> = viewSearch.queryChanges()
}
