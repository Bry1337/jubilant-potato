package com.tickr.tickr.managers.prefs

import android.content.SharedPreferences
import androidx.content.edit
import org.apache.commons.lang3.StringUtils

/**
 * Created by bry1337 on 20/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class SharedPreferenceManager(var sharedPreferences: SharedPreferences) {

  fun logUserIn() {
    sharedPreferences.edit { putBoolean(SharedPreferenceKeys.IS_USER_LOGGED_IN, true) }
  }

  fun saveFullName(fullName: String) {
    sharedPreferences.edit {
      putString(SharedPreferenceKeys.FULL_NAME, fullName)
    }
  }

  fun savePhotoUri(url: String) {
    sharedPreferences.edit {
      putString(SharedPreferenceKeys.PHOTO_URL, url)
    }
  }

  fun saveUID(uid: String) {
    sharedPreferences.edit {
      putString(SharedPreferenceKeys.UID, uid)
    }
  }

  fun saveEmail(email: String) {
    sharedPreferences.edit {
      putString(SharedPreferenceKeys.EMAIL, email)
    }
  }

  fun isUserLoggedIn(): Boolean {
    return sharedPreferences.getBoolean(SharedPreferenceKeys.IS_USER_LOGGED_IN, false)
  }

  fun getFullName(): String {
    return sharedPreferences.getString(SharedPreferenceKeys.FULL_NAME, StringUtils.EMPTY)
  }

  fun getPhotoUri(): String {
    return sharedPreferences.getString(SharedPreferenceKeys.PHOTO_URL, StringUtils.EMPTY)
  }

  fun getUID(): String {
    return sharedPreferences.getString(SharedPreferenceKeys.UID, StringUtils.EMPTY)
  }

  fun getEmail(): String {
    return sharedPreferences.getString(SharedPreferenceKeys.EMAIL, StringUtils.EMPTY)
  }

  fun clearSharedPreferences() {
    sharedPreferences.edit { clear() }
  }
}