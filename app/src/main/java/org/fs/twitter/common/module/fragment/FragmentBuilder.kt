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

import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap
import org.fs.twitter.common.component.fragment.TweetDetailFragmentComponent
import org.fs.twitter.common.component.fragment.TweetListFragmentComponent
import org.fs.twitter.view.TweetDetailFragment
import org.fs.twitter.view.TweetListFragment

@Module
abstract class FragmentBuilder {

  @Binds @IntoMap @FragmentKey(TweetListFragment::class)
  abstract fun bindTweetListFragment(builder: TweetListFragmentComponent.Builder): AndroidInjector.Factory<out Fragment>

  @Binds @IntoMap @FragmentKey(TweetDetailFragment::class)
  abstract fun bindTweetDetailFragment(builder: TweetDetailFragmentComponent.Builder): AndroidInjector.Factory<out Fragment>
}