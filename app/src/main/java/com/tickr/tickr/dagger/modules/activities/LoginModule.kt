package com.tickr.tickr.dagger.modules.activities

import android.support.v7.app.AlertDialog
import com.tickr.tickr.api.managers.ApiManager
import com.tickr.tickr.dagger.scopes.UserScope
import com.tickr.tickr.ui.activities.login.LoginActivity
import com.tickr.tickr.ui.activities.login.LoginPresenter
import dagger.Module
import dagger.Provides
import rx.subscriptions.CompositeSubscription
import javax.inject.Singleton

/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@Singleton
@Module
class LoginModule(var activity: LoginActivity) {

  @Provides
  @UserScope
  fun provideLoginActivity(): LoginActivity {
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
  fun provideLoginPresenter(apiManager: ApiManager): LoginPresenter {
    return LoginPresenter(activity, apiManager)
  }

}