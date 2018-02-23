package com.tickr.tickr.dagger.components.activities

import com.tickr.tickr.dagger.modules.activities.PlatformListModule
import com.tickr.tickr.dagger.modules.api.UserApiModule
import com.tickr.tickr.dagger.scopes.UserScope
import com.tickr.tickr.ui.activities.newslist.PlatformListActivity
import dagger.Subcomponent

/**
 * Created by bry1337 on 23/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@UserScope
@Subcomponent(modules = [PlatformListModule::class, UserApiModule::class])
interface PlatformListComponent {
  fun inject(platformListActivity: PlatformListActivity): PlatformListActivity
}