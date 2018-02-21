package com.tickr.tickr.managers.prefs

import android.content.SharedPreferences
import androidx.content.edit

/**
 * Created by bry1337 on 20/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class SharedPreferenceManager(var sharedPreferences: SharedPreferences) {

  fun logUserIn() {
    sharedPreferences.edit { putBoolean(SharedPreferenceKeys.IS_USER_LOGGED_IN, true) }
  }
}