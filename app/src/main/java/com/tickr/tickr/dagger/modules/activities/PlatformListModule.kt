package com.tickr.tickr.dagger.modules.activities

import androidx.appcompat.app.AlertDialog
import com.tickr.tickr.api.managers.ApiManager
import com.tickr.tickr.dagger.scopes.UserScope
import com.tickr.tickr.ui.activities.newslist.PlatformListActivity
import com.tickr.tickr.ui.activities.newslist.PlatformListPresenter
import dagger.Module
import dagger.Provides
import rx.subscriptions.CompositeSubscription

/**
 * Created by bry1337 on 23/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@UserScope
@Module
class PlatformListModule(var activity: PlatformListActivity) {

  @Provides
  @UserScope
  fun providePlatformListActivity(): PlatformListActivity {
    return activity
  }

  @Provides
  @UserScope
  fun providePlatformListPresenter(apiManager: ApiManager): PlatformListPresenter {
    return PlatformListPresenter(activity, apiManager)
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