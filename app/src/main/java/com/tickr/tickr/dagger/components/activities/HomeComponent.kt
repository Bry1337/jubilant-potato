package com.tickr.tickr.dagger.components.activities

import com.tickr.tickr.dagger.modules.activities.HomeModule
import com.tickr.tickr.dagger.modules.api.UserApiModule
import com.tickr.tickr.dagger.scopes.UserScope
import com.tickr.tickr.ui.activities.home.HomeActivity
import dagger.Subcomponent

/**
 * Created by bry1337 on 20/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@UserScope
@Subcomponent(modules = [(HomeModule::class), (UserApiModule::class)])
interface HomeComponent {
  fun inject(homeActivity: HomeActivity): HomeActivity
}