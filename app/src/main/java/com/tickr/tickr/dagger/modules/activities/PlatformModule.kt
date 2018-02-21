package com.tickr.tickr.dagger.modules.activities

import android.support.v7.app.AlertDialog
import com.tickr.tickr.api.managers.ApiManager
import com.tickr.tickr.dagger.scopes.UserScope
import com.tickr.tickr.ui.activities.platform.PlatformActivity
import com.tickr.tickr.ui.activities.platform.PlatformPresenter
import dagger.Module
import dagger.Provides
import rx.subscriptions.CompositeSubscription

/**
 * Created by bry1337 on 21/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@UserScope
@Module
class PlatformModule(var activity: PlatformActivity) {

  @Provides
  @UserScope
  fun providePlatformActivity(): PlatformActivity {
    return activity
  }

  @Provides
  @UserScope
  fun providePlatformPresenter(apiManager: ApiManager): PlatformPresenter {
    return PlatformPresenter(activity, apiManager)
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