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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.fs.mvp.core.AbstractFragment
import org.fs.twitter.R
import org.fs.twitter.presenter.TweetListFragmentPresenter

class TweetListFragment : AbstractFragment<TweetListFragmentPresenter>(), TweetListFragmentView {

  override fun onCreateView(factory: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? = factory.inflate(R.layout.view_tweet_list_fragment, parent, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    presenter.restoreState(savedInstanceState ?: arguments)
    presenter.onCreate()
  }
}
