package com.potato.jubilantpotato.application

import android.content.Context
import com.potato.jubilantpotato.dagger.components.ApplicationComponent
import com.potato.jubilantpotato.dagger.components.DaggerApplicationComponent
import com.potato.jubilantpotato.dagger.components.LoginComponent
import com.potato.jubilantpotato.dagger.modules.ApplicationModule
import com.potato.jubilantpotato.dagger.modules.activities.LoginModule
import com.potato.jubilantpotato.ui.activities.login.LoginActivity

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