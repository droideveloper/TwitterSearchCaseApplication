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
package org.fs.twitter.net

import io.reactivex.Observable
import org.fs.twitter.model.Authorization
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Endpoint {

  @GET("1.1/search/tweets.json") fun search(@Header("Authorization") authorization: Authorization, @Query("lang") lang: String, @Query("q") q: String, @Query("result_type") resultType: String = "mixed"): Observable<String>
}