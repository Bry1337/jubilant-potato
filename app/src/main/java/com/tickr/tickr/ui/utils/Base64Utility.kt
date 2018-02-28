package com.tickr.tickr.ui.utils

import android.content.ContentValues.TAG
import android.util.Base64
import android.util.Log
import java.io.UnsupportedEncodingException

/**
 * Created by bry1337 on 27/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class Base64Utility {
  companion object {
    fun encodeTextWithBase64(text: String): String {
      var data = ByteArray(0)
      try {
        data = text.toByteArray(charset("UTF-8"))
      } catch (e: UnsupportedEncodingException) {
        Log.e(TAG, e.message)
      }

      return Base64.encodeToString(data, Base64.NO_WRAP)
    }
  }
}