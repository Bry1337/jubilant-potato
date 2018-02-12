package com.potato.jubilantpotato.application

import android.content.Context
import com.potato.jubilantpotato.dagger.components.ApplicationComponent
import com.potato.jubilantpotato.dagger.components.DaggerApplicationComponent
import com.potato.jubilantpotato.dagger.modules.ApplicationModule

/**
 * Created by bry1337 on 23/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class JubilantPotatoApplication : BaseApplication() {

  private lateinit var applicationComponent: ApplicationComponent

  companion object {
    operator fun get(context: Context): JubilantPotatoApplication {
      return context.applicationContext as JubilantPotatoApplication
    }
  }

  override fun onCreate() {
    super.onCreate()
    initAppComponent()
  }


  private fun initAppComponent() {
    applicationComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build();
  }
}