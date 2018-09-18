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
package org.fs.twitter.common.module.fragment

import dagger.Binds
import dagger.Module
import org.fs.architecture.common.scope.ForFragment
import org.fs.twitter.presenter.TweetDetailFragmentPresenter
import org.fs.twitter.presenter.TweetDetailFragmentPresenterImp
import org.fs.twitter.presenter.TweetListFragmentPresenter
import org.fs.twitter.presenter.TweetListFragmentPresenterImp
import org.fs.twitter.view.TweetDetailFragment
import org.fs.twitter.view.TweetDetailFragmentView
import org.fs.twitter.view.TweetListFragment
import org.fs.twitter.view.TweetListFragmentView

@Module
abstract class FragmentModule {

  @ForFragment @Binds abstract fun bindTweetFragmentListView(fragment: TweetListFragment): TweetListFragmentView
  @ForFragment @Binds abstract fun bindTweetFragmentListPresenter(presenter: TweetListFragmentPresenterImp): TweetListFragmentPresenter

  //@ForFragment @Provides fun provideDataSet(): ObservableList<Tweet> = ObservableList()

  @ForFragment @Binds abstract fun bindTweetDetailFragmentView(fragment: TweetDetailFragment): TweetDetailFragmentView
  @ForFragment @Binds abstract fun bindTweetDetailFragmentPresenter(presenter: TweetDetailFragmentPresenterImp): TweetDetailFragmentPresenter
}