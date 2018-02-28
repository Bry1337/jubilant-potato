package com.tickr.tickr.dagger.modules.activities

import android.support.v7.app.AlertDialog
import com.tickr.tickr.api.managers.ApiManager
import com.tickr.tickr.dagger.scopes.UserScope
import com.tickr.tickr.ui.activities.bookmark.BookmarkActivity
import com.tickr.tickr.ui.activities.bookmark.BookmarkPresenter
import dagger.Module
import dagger.Provides
import rx.subscriptions.CompositeSubscription

/**
 * Created by bry1337 on 28/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@UserScope
@Module
class BookmarkModule(var activity: BookmarkActivity) {

  @Provides
  @UserScope
  fun provideBookmarkActivity(): BookmarkActivity {
    return activity
  }

  @Provides
  @UserScope
  fun provideBookmarkPresenter(apiManager: ApiManager): BookmarkPresenter {
    return BookmarkPresenter(activity, apiManager)
  }

  @Provides
  @UserScope
  fun provideAlertDialog(): AlertDialog {
    return AlertDialog.Builder(activity).create()
  }

  @Provides
  @UserScope
  fun provideCompositeSubscription(): CompositeSubscription {
    return CompositeSubscription()
  }

}