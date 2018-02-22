package com.tickr.tickr.dagger.modules.activities

import android.support.v7.app.AlertDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tickr.tickr.api.managers.ApiManager
import com.tickr.tickr.dagger.scopes.UserScope
import com.tickr.tickr.ui.activities.home.HomeActivity
import com.tickr.tickr.ui.activities.home.HomePresenter
import dagger.Module
import dagger.Provides
import rx.subscriptions.CompositeSubscription

/**
 * Created by bry1337 on 20/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@UserScope
@Module
class HomeModule(var activity: HomeActivity) {


  @Provides
  @UserScope
  fun provideActivity(): HomeActivity {
    return activity
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

  @Provides
  @UserScope
  fun provideHomePresenter(apiManager: ApiManager): HomePresenter {
    return HomePresenter(activity, apiManager)
  }



}