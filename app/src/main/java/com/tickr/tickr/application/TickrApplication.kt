package com.tickr.tickr.application

import android.content.Context
import com.tickr.tickr.dagger.components.ApplicationComponent
import com.tickr.tickr.dagger.components.DaggerApplicationComponent
import com.tickr.tickr.dagger.components.activities.*
import com.tickr.tickr.dagger.modules.ApplicationModule
import com.tickr.tickr.dagger.modules.activities.*
import com.tickr.tickr.ui.activities.detailednews.DetailedNewsActivity
import com.tickr.tickr.ui.activities.home.HomeActivity
import com.tickr.tickr.ui.activities.login.LoginActivity
import com.tickr.tickr.ui.activities.newslist.PlatformListActivity
import com.tickr.tickr.ui.activities.platform.PlatformActivity

/**
 * Created by bry1337 on 23/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class TickrApplication : BaseApplication() {

  private lateinit var applicationComponent: ApplicationComponent
  private var loginComponent: LoginComponent? = null
  private var homeComponent: HomeComponent? = null
  private var platformComponent: PlatformComponent? = null
  private var detailedNewsComponent: DetailedNewsComponent? = null
  private var platformListComponent: PlatformListComponent? = null

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

  fun createHomeComponent(homeActivity: HomeActivity): HomeComponent {
    homeComponent = applicationComponent.plus(HomeModule(homeActivity))
    return homeComponent as HomeComponent
  }

  fun createPlatformComponent(platformActivity: PlatformActivity): PlatformComponent {
    platformComponent = applicationComponent.plus(PlatformModule(platformActivity))
    return platformComponent as PlatformComponent
  }

  fun createDetailedNewsComponent(detailedNewsActivity: DetailedNewsActivity): DetailedNewsComponent {
    detailedNewsComponent = applicationComponent.plus(DetailedNewsModule(detailedNewsActivity))
    return detailedNewsComponent as DetailedNewsComponent
  }

  fun createPlatformListComponent(platformListActivity: PlatformListActivity): PlatformListComponent {
    platformListComponent = applicationComponent.plus(PlatformListModule(platformListActivity))
    return platformListComponent as PlatformListComponent
  }

  fun releaseLoginComponent() {
    loginComponent = null
  }

  fun releaseHomeComponent() {
    homeComponent = null
  }

  fun releasePlatformComponent() {
    platformComponent = null
  }

  fun releaseDetailedNewsComponent() {
    detailedNewsComponent = null
  }

  fun releasePlatformListComponent(){
    platformListComponent = null
  }
}