package com.tickr.tickr.dagger.components

import com.tickr.tickr.dagger.modules.ApplicationModule
import com.tickr.tickr.dagger.modules.activities.LoginModule
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
}
