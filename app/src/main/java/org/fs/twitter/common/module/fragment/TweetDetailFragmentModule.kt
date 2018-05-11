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

import dagger.Module
import dagger.Provides
import org.fs.mvp.common.scope.ForFragment
import org.fs.twitter.presenter.TweetDetailFragmentPresenter
import org.fs.twitter.presenter.TweetDetailFragmentPresenterImp
import org.fs.twitter.view.TweetDetailFragment
import org.fs.twitter.view.TweetDetailFragmentView

@Module
class TweetDetailFragmentModule {

  @ForFragment @Provides fun provideTweetDetailFragmentView(fragment: TweetDetailFragment): TweetDetailFragmentView = fragment
  @ForFragment @Provides fun provideTweetDetailFragmentPresenter(view: TweetDetailFragmentView): TweetDetailFragmentPresenter = TweetDetailFragmentPresenterImp(view)

}