package com.tickr.tickr.application

import android.content.Context
import com.tickr.tickr.dagger.components.ApplicationComponent
import com.tickr.tickr.dagger.components.DaggerApplicationComponent
import com.tickr.tickr.dagger.components.LoginComponent
import com.tickr.tickr.dagger.modules.ApplicationModule
import com.tickr.tickr.dagger.modules.activities.LoginModule
import com.tickr.tickr.ui.activities.login.LoginActivity

/**
 * Created by bry1337 on 23/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class JubilantPotatoApplication : BaseApplication() {

  private lateinit var applicationComponent: ApplicationComponent
  private var loginComponent: LoginComponent? = null

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
    applicationComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
  }

  fun createLoginComponent(loginActivity: LoginActivity): LoginComponent {
    loginComponent = applicationComponent.plus(LoginModule(loginActivity))
    return loginComponent as LoginComponent
  }

  fun releaseLoginComponent() {
    loginComponent = null
  }
}