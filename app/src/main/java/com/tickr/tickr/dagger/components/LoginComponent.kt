package com.tickr.tickr.dagger.components

import com.tickr.tickr.dagger.modules.activities.LoginModule
import com.tickr.tickr.dagger.modules.api.UserApiModule
import com.tickr.tickr.dagger.scopes.UserScope
import com.tickr.tickr.ui.activities.login.LoginActivity
import dagger.Subcomponent

/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@UserScope
@Subcomponent(modules = arrayOf(LoginModule::class, UserApiModule::class))
interface LoginComponent {
  fun inject(loginActivity: LoginActivity): LoginActivity
}