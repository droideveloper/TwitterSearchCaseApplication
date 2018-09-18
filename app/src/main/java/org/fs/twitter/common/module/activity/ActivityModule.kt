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

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.fs.architecture.common.scope.ForActivity
import org.fs.architecture.common.scope.ForFragment
import org.fs.twitter.common.module.fragment.FragmentModule
import org.fs.twitter.common.module.fragment.ProviderFragmentModule
import org.fs.twitter.presenter.MainActivityPresenter
import org.fs.twitter.presenter.MainActivityPresenterImp
import org.fs.twitter.presenter.TweetDetailActivityPresenter
import org.fs.twitter.presenter.TweetDetailActivityPresenterImp
import org.fs.twitter.view.*

@Module
abstract class ActivityModule {

  @ForActivity @Binds abstract fun bindMainActivityView(activity: MainActivity): MainActivityView
  @ForActivity @Binds abstract fun bindMainActivityPresenter(presenter: MainActivityPresenterImp): MainActivityPresenter

  @ForActivity @Binds abstract fun bindTweetDetailActivityView(activity: TweetDetailActivity): TweetDetailActivityView
  @ForActivity @Binds abstract fun bindTweetDetailActivityPresenter(presenter: TweetDetailActivityPresenterImp): TweetDetailActivityPresenter

  @ForFragment @ContributesAndroidInjector(modules = [FragmentModule::class, ProviderFragmentModule::class])
  abstract fun bindTweetListFragment(): TweetListFragment

  @ForFragment @ContributesAndroidInjector(modules = [FragmentModule::class, ProviderFragmentModule::class])
  abstract fun bindTweetDetailFragment(): TweetDetailFragment
}