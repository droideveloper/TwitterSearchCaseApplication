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
package org.fs.twitter.common.component

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import org.fs.twitter.TwitterSearchCaseApplication
import org.fs.twitter.common.module.AppModule
import org.fs.twitter.common.module.ProviderAppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ProviderAppModule::class, AppModule::class])
interface AppComponent: AndroidInjector<TwitterSearchCaseApplication> {

  @Component.Builder
  abstract class Builder: AndroidInjector.Builder<TwitterSearchCaseApplication>()
}