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
package org.fs.twitter

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.fs.mvp.net.RxJava2CallAdaptorFactory
import org.fs.mvp.net.converters.GsonConverterFactory
import org.fs.twitter.model.Authorization
import org.fs.twitter.net.Endpoint
import org.fs.twitter.util.async
import retrofit2.Retrofit

class TestActivity: AppCompatActivity() {

  val disposeBag = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(FrameLayout(this))

    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.HEADERS

    val okhttpClient = OkHttpClient.Builder()
      .addInterceptor(logger)
      .build()

    val retrofit = Retrofit.Builder()
      .baseUrl("https://api.twitter.com/")
      .client(okhttpClient)
      .addCallAdapterFactory(RxJava2CallAdaptorFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    val query = "nasa"

    val endpoint = retrofit.create(Endpoint::class.java)
    val disposable = endpoint.loadMore(Authorization.create(query, "995013571133300736"), Uri.encode(query), "995013571133300736")
      .async()
      .subscribe({
        val statustes = it.statuses
        if (statustes != null) {

        }
      }, { it.printStackTrace() })

    disposeBag.add(disposable)

  }


  override fun onStop() {
    super.onStop()
    disposeBag.clear()
  }

}