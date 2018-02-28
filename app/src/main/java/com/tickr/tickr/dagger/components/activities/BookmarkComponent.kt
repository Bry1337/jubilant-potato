package com.tickr.tickr.dagger.components.activities

import com.tickr.tickr.dagger.modules.activities.BookmarkModule
import com.tickr.tickr.dagger.modules.api.UserApiModule
import com.tickr.tickr.dagger.scopes.UserScope
import com.tickr.tickr.ui.activities.bookmark.BookmarkActivity
import dagger.Subcomponent

/**
 * Created by bry1337 on 28/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@UserScope
@Subcomponent(modules = [BookmarkModule::class, UserApiModule::class])
interface BookmarkComponent {
  fun inject(bookmarkActivity: BookmarkActivity): BookmarkActivity
}