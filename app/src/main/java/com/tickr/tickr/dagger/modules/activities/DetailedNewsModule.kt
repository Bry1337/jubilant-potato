package com.tickr.tickr.dagger.modules.activities

import android.support.v7.app.AlertDialog
import com.tickr.tickr.api.managers.ApiManager
import com.tickr.tickr.dagger.scopes.UserScope
import com.tickr.tickr.ui.activities.detailednews.DetailedNewsActivity
import com.tickr.tickr.ui.activities.detailednews.DetailedNewsPresenter
import dagger.Module
import dagger.Provides
import rx.subscriptions.CompositeSubscription

/**
 * Created by bry1337 on 22/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@UserScope
@Module
class DetailedNewsModule(var activity: DetailedNewsActivity) {


  @Provides
  @UserScope
  fun provideDetailedNewsActivity(): DetailedNewsActivity {
    return activity;
  }

  @Provides
  @UserScope
  fun provideDetailedNewsPresenter(apiManager: ApiManager): DetailedNewsPresenter {
    return DetailedNewsPresenter(activity, apiManager)
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