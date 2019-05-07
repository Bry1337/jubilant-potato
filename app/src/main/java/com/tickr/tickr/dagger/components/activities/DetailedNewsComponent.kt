package com.tickr.tickr.dagger.components.activities

import com.tickr.tickr.dagger.modules.activities.DetailedNewsModule
import com.tickr.tickr.dagger.modules.api.UserApiModule
import com.tickr.tickr.dagger.scopes.UserScope
import com.tickr.tickr.ui.activities.detailednews.DetailedNewsActivity
import dagger.Subcomponent

/**
 * Created by bry1337 on 22/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@UserScope
@Subcomponent(modules = [DetailedNewsModule::class, UserApiModule::class])
interface DetailedNewsComponent {
  fun inject(detailedNewsActivity: DetailedNewsActivity): DetailedNewsActivity
}