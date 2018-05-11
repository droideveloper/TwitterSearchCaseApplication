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
package org.fs.twitter.model

import android.net.Uri
import org.fs.twitter.util.*

class Authorization private constructor() {

  companion object {
    private const val HTTP_METHOD_GET = "GET"

    // twitter api values
    private const val TWITTER_CONSUMER_KEY = "S97HvOn9Rm5BNES72C7eLGPsG"
    private const val TWITTER_CONSUMER_SECRET = "r7pVbJsAgWa6NhU6l9MKpTA4pK9GgaqZVrutoF6tcn3WfoDjb8"
    private const val TWITTER_ACCESS_TOKEN = "1556110866-rsuDg1astuYrnx04EfdDMT3klZaFFnGpLAmD1FG"
    private const val TWITTER_ACCESS_TOKEN_SECRET = "fEQ3gaYL6bObkikvfLpKuiWh84T0y2mapjAS9qffnmD7O"

    private const val OAUTH_CONSUMER_KEY = "oauth_consumer_key"
    private const val OAUTH_NONCE = "oauth_nonce"
    private const val OAUTH_SIGNATURE_METHOD = "oauth_signature_method"
    private const val OAUTH_TIMESTAMP = "oauth_timestamp"
    private const val OAUTH_VERSION = "oauth_version"
    private const val OAUTH_TOKEN = "oauth_token"
    private const val LANG = "lang"
    private const val RESULT_TYPE = "result_type"
    private const val SINCE_ID = "since_id"
    private const val QUERY = "q"

    private const val DEFAULT_LANG = "en"
    private const val OAUTH_SIGNATURE = "oauth_signature"
    private const val OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1"
    private const val RESULT_TYPE_VALUE = "mixed"

    private fun toParamString(query: String, nonce: String, timeStamp: String): String =
      "$OAUTH_CONSUMER_KEY=$TWITTER_CONSUMER_KEY" +
      "&$OAUTH_NONCE=$nonce" +
      "&$OAUTH_SIGNATURE_METHOD=$OAUTH_SIGNATURE_METHOD_VALUE" +
      "&$OAUTH_TIMESTAMP=$timeStamp" +
      "&$OAUTH_TOKEN=${Uri.parse(TWITTER_ACCESS_TOKEN)}" +
      "&$OAUTH_VERSION=1.0" +
      "&$QUERY=${Uri.encode(query)}"

    private fun toParamString(query: String, since_id: String, nonce: String, timeStamp: String): String =
        "$OAUTH_CONSUMER_KEY=$TWITTER_CONSUMER_KEY" +
        "&$OAUTH_NONCE=$nonce" +
        "&$OAUTH_SIGNATURE_METHOD=$OAUTH_SIGNATURE_METHOD_VALUE" +
        "&$OAUTH_TIMESTAMP=$timeStamp" +
        "&$OAUTH_TOKEN=${Uri.parse(TWITTER_ACCESS_TOKEN)}" +
        "&$OAUTH_VERSION=1.0" +
        "&$QUERY=${Uri.encode(query)}" +
        "&$SINCE_ID=$since_id"


    fun create(query: String = String.EMPTY) : Authorization {
      val auth = Authorization()
      auth.parameterString = toParamString(query, auth.nonce, auth.timeStamp)
      val baseString = "$HTTP_METHOD_GET&${Uri.encode(TWITTER_ENDPOINT)}&${Uri.encode(auth.parameterString)}"
      auth.oauthSignature = baseString.computeSignature("$TWITTER_CONSUMER_SECRET&${Uri.encode(TWITTER_ACCESS_TOKEN_SECRET)}")
      return auth
    }

    fun create(query: String = String.EMPTY, since_id: String = String.EMPTY) : Authorization {
      val auth = Authorization()
      auth.parameterString = toParamString(query, since_id, auth.nonce, auth.timeStamp)
      val baseString = "$HTTP_METHOD_GET&${Uri.encode(TWITTER_ENDPOINT)}&${Uri.encode(auth.parameterString)}"
      auth.oauthSignature = baseString.computeSignature("$TWITTER_CONSUMER_SECRET&${Uri.encode(TWITTER_ACCESS_TOKEN_SECRET)}")
      return auth
    }
  }

  private lateinit var parameterString: String
  private lateinit var oauthSignature: String

  private var nonce = newNonce()
  private var timeStamp = currentTime()

  private fun authorizationHeader(nonce: String, timeStamp: String): String = "OAuth $OAUTH_CONSUMER_KEY=\"$TWITTER_CONSUMER_KEY\"," +
    "$OAUTH_SIGNATURE_METHOD=\"$OAUTH_SIGNATURE_METHOD_VALUE\"," +
    "$OAUTH_TOKEN=\"${Uri.encode(TWITTER_ACCESS_TOKEN)}\"," +
    "$OAUTH_TIMESTAMP=\"$timeStamp\","+
    "$OAUTH_NONCE=\"$nonce\"," +
    "$OAUTH_VERSION=\"1.0\"," +
    "$OAUTH_SIGNATURE=\"${Uri.encode(oauthSignature)}\""

  override fun toString(): String {
    return authorizationHeader(nonce, timeStamp)
  }
}