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
package org.fs.twitter.common.module.activity

import dagger.Module
import dagger.Provides
import org.fs.architecture.common.NavigationType
import org.fs.architecture.common.scope.ForActivity
import org.fs.twitter.R
import org.fs.twitter.common.HandsetNavigation
import org.fs.twitter.common.TabletNavigation
import org.fs.twitter.model.Tweet
import org.fs.twitter.presenter.MainActivityPresenter
import org.fs.twitter.presenter.MainActivityPresenterImp
import org.fs.twitter.presenter.TweetDetailActivityPresenter
import org.fs.twitter.presenter.TweetDetailActivityPresenterImp
import org.fs.twitter.view.MainActivity
import org.fs.twitter.view.MainActivityView
import org.fs.twitter.view.TweetDetailActivity
import org.fs.twitter.view.TweetDetailActivityView

@Module
class ProviderActivityModule {

  @ForActivity
  @Provides fun provideMainActivityView(activity: MainActivity): MainActivityView = activity

  @ForActivity @Provides fun provideNavigationType(activity: MainActivity): NavigationType<Tweet> {
    val handset = activity.resources.getBoolean(R.bool.handset);
    if (handset) {
      return HandsetNavigation(activity)
    } else {
      return TabletNavigation()
    }
  }

  @ForActivity @Provides fun provideMainActivityPresenter(view: MainActivityView, navigationType: NavigationType<Tweet>): MainActivityPresenter = MainActivityPresenterImp(view, navigationType)

  @ForActivity
  @Provides fun provideTweetDetailActivityView(activity: TweetDetailActivity): TweetDetailActivityView = activity
  @ForActivity @Provides fun provideTweetDetailActivityPrenseter(view: TweetDetailActivityView): TweetDetailActivityPresenter = TweetDetailActivityPresenterImp(view)
}