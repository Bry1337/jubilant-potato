package com.potato.jubilantpotato.application

import android.app.Application
import android.content.Context

/**
 * Created by bry1337 on 23/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
open class BaseApplication : Application(){
  override fun onCreate() {
    super.onCreate()

  }

  override fun attachBaseContext(base: Context?) {
    super.attachBaseContext(base)
  }
}
