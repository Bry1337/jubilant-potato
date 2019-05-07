package com.tickr.tickr.dagger.components.activities

import com.tickr.tickr.dagger.modules.activities.PlatformModule
import com.tickr.tickr.dagger.modules.api.UserApiModule
import com.tickr.tickr.dagger.scopes.UserScope
import com.tickr.tickr.ui.activities.platform.PlatformActivity
import dagger.Subcomponent

/**
 * Created by bry1337 on 21/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@UserScope
@Subcomponent(modules = [PlatformModule::class, UserApiModule::class])
interface PlatformComponent {
  fun inject(platformActivity: PlatformActivity): PlatformActivity
}