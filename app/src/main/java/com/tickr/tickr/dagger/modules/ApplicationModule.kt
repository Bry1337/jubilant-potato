package com.tickr.tickr.dagger.modules

import android.app.Application
import android.content.Context
import com.tickr.tickr.managers.AppActivityManager
import com.tickr.tickr.managers.prefs.SharedPreferenceKeys
import com.tickr.tickr.managers.prefs.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by bry1337 on 25/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@Singleton
@Module
class ApplicationModule(private val application: Application) {

  @Provides
  @Singleton
  internal fun provideApplication(): Application {
    return application
  }

  @Provides
  @Singleton
  internal fun provideAppActivitymanager(): AppActivityManager {
    return AppActivityManager()
  }

  @Provides
  @Singleton
  internal fun provideSharedPreferenceManager(): SharedPreferenceManager {
    return SharedPreferenceManager(
        application.applicationContext.getSharedPreferences(SharedPreferenceKeys.MY_PREFERENCE, Context.MODE_PRIVATE))
  }
}
