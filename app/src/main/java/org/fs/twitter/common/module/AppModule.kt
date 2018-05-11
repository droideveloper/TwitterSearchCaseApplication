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

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.fs.mvp.net.RxJava2CallAdaptorFactory
import org.fs.mvp.net.converters.GsonConverterFactory
import org.fs.twitter.BuildConfig
import org.fs.twitter.common.component.activity.MainActivityComponent
import org.fs.twitter.common.component.activity.TweetDetailActivityComponent
import org.fs.twitter.common.component.fragment.TweetDetailFragmentComponent
import org.fs.twitter.common.component.fragment.TweetListFragmentComponent
import org.fs.twitter.net.Endpoint
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(subcomponents = [
  MainActivityComponent::class, TweetDetailActivityComponent::class,
  TweetListFragmentComponent::class, TweetDetailFragmentComponent::class])
class AppModule {

  companion object {
    private const val BASE_URL = "https://api.twitter.com/"
  }

  @Singleton @Provides fun provideOkHttp(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
      val logger = HttpLoggingInterceptor()
      logger.level = HttpLoggingInterceptor.Level.BODY
      builder.addInterceptor(logger)
    }
    return builder.build()
  }

  @Singleton @Provides fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
    .addCallAdapterFactory(RxJava2CallAdaptorFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .baseUrl(BASE_URL)
    .build()

  @Singleton @Provides fun provideEndpoint(retrofit: Retrofit): Endpoint = retrofit.create(Endpoint::class.java)
}