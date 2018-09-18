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
package org.fs.twitter.common.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.fs.architecture.common.scope.ForActivity
import org.fs.twitter.TwitterSearchCaseApplication
import org.fs.twitter.common.module.activity.ActivityModule
import org.fs.twitter.common.module.activity.ProviderActivityModule
import org.fs.twitter.view.MainActivity
import org.fs.twitter.view.TweetDetailActivity

@Module
abstract class AppModule {

  @Binds abstract fun provideApplication(app: TwitterSearchCaseApplication): Application
  @Binds abstract fun provideContext(app: Application): Context

  @ForActivity @ContributesAndroidInjector(modules = [ActivityModule::class, ProviderActivityModule::class])
  abstract fun bindMainActivity(): MainActivity

  @ForActivity @ContributesAndroidInjector(modules = [ActivityModule::class, ProviderActivityModule::class])
  abstract fun bindTweetDetailActivity(): TweetDetailActivity
}