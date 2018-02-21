package com.tickr.tickr.dagger.components

import com.tickr.tickr.dagger.components.activities.HomeComponent
import com.tickr.tickr.dagger.components.activities.LoginComponent
import com.tickr.tickr.dagger.components.activities.PlatformComponent
import com.tickr.tickr.dagger.modules.ApplicationModule
import com.tickr.tickr.dagger.modules.activities.HomeModule
import com.tickr.tickr.dagger.modules.activities.LoginModule
import com.tickr.tickr.dagger.modules.activities.PlatformModule
import com.tickr.tickr.dagger.modules.api.ApiModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by bry1337 on 25/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, ApiModule::class))
interface ApplicationComponent {
  fun plus(loginModule: LoginModule): LoginComponent

  fun plus(homeModule: HomeModule): HomeComponent

  fun plus(platformModule: PlatformModule): PlatformComponent
}
