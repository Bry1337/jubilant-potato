package com.potato.jubilantpotato.dagger.modules

import android.app.Application
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
  @Singleton internal fun provideApplication(): Application {
    return application
  }
}
