package com.potato.jubilantpotato.dagger.components

import com.potato.jubilantpotato.dagger.modules.activities.LoginModule
import com.potato.jubilantpotato.dagger.modules.api.UserApiModule
import com.potato.jubilantpotato.dagger.scopes.UserScope
import com.potato.jubilantpotato.ui.activities.login.LoginActivity
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