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

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import org.fs.twitter.common.component.activity.MainActivityComponent
import org.fs.twitter.common.component.activity.TweetDetailActivityComponent
import org.fs.twitter.view.MainActivity
import org.fs.twitter.view.TweetDetailActivity

@Module
abstract class ActivityBuilder {

  @Binds @IntoMap @ActivityKey(MainActivity::class)
  abstract fun buildMainActivity(builder: MainActivityComponent.Builder): AndroidInjector.Factory<out Activity>

  @Binds @IntoMap @ActivityKey(TweetDetailActivity::class)
  abstract  fun buildTweetDetailActivity(builder: TweetDetailActivityComponent.Builder): AndroidInjector.Factory<out Activity>
}