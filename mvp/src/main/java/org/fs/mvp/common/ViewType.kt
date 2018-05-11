/*
 * MVP Android Kotlin Copyright (C) 2017 Fatih, Brokoli Labs.
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
package org.fs.mvp.common

import android.content.Context
import android.content.Intent
import android.view.View

interface ViewType {

  fun showProgress()
  fun hideProgress()
  fun showError(msg: String)
  fun showError(msg: String, action: String, callback: View.OnClickListener?)
  fun getStringResource(stringRes: Int): String?
  fun startActivity(intent: Intent)
  fun startActivityForResult(intent: Intent, requestCode: Int)
  fun requestPermissions(permissions: Array<String>, requestCode: Int)
  fun finish()
  fun isAvailable(): Boolean
  fun getContext(): Context?
}