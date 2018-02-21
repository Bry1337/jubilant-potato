package com.tickr.tickr.application

import android.content.Context
import com.tickr.tickr.dagger.components.ApplicationComponent
import com.tickr.tickr.dagger.components.DaggerApplicationComponent
import com.tickr.tickr.dagger.components.activities.HomeComponent
import com.tickr.tickr.dagger.components.activities.LoginComponent
import com.tickr.tickr.dagger.modules.ApplicationModule
import com.tickr.tickr.dagger.modules.activities.HomeModule
import com.tickr.tickr.dagger.modules.activities.LoginModule
import com.tickr.tickr.ui.activities.home.HomeActivity
import com.tickr.tickr.ui.activities.login.LoginActivity

/**
 * Created by bry1337 on 23/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class TickrApplication : BaseApplication() {

  private lateinit var applicationComponent: ApplicationComponent
  private var loginComponent: LoginComponent? = null
  private var homeComponent: HomeComponent? = null

  companion object {
    operator fun get(context: Context): TickrApplication {
      return context.applicationContext as TickrApplication
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

  fun createHomeComponent(homeActivity: HomeActivity): HomeComponent{
    homeComponent = applicationComponent.plus(HomeModule(homeActivity))
    return homeComponent as HomeComponent
  }

  fun releaseLoginComponent() {
    loginComponent = null
  }

  fun releaseHomeComponent(){
    homeComponent = null
  }
}