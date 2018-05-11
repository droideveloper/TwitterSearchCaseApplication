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
package org.fs.twitter.common.component.activity

import dagger.Subcomponent
import dagger.android.AndroidInjector
import org.fs.mvp.common.scope.ForActivity
import org.fs.twitter.common.module.activity.TweetDetailActivityModule
import org.fs.twitter.view.TweetDetailActivity

@ForActivity
@Subcomponent(modules = [TweetDetailActivityModule::class])
interface TweetDetailActivityComponent: AndroidInjector<TweetDetailActivity> {

  @Subcomponent.Builder
  abstract class Builder: AndroidInjector.Builder<TweetDetailActivity>()
}