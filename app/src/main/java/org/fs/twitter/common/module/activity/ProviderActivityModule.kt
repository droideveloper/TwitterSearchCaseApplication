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
import org.fs.twitter.view.MainActivity

@Module
class ProviderActivityModule {

  @ForActivity @Provides fun provideNavigationType(activity: MainActivity): NavigationType<Tweet> {
    val handset = activity.resources.getBoolean(R.bool.handset)
    return if (handset) {
      HandsetNavigation(activity)
    } else {
      TabletNavigation()
    }
  }
}