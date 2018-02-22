package com.tickr.tickr.dagger.modules

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tickr.tickr.BuildConfig
import com.tickr.tickr.dagger.scopes.UserScope
import com.tickr.tickr.managers.AppActivityManager
import com.tickr.tickr.managers.prefs.SharedPreferenceKeys
import com.tickr.tickr.managers.prefs.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by bry1337 on 25/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@Singleton
@Module
class ApplicationModule(private val application: Application) {

  @Provides
  @Singleton
  internal fun provideApplication(): Application {
    return application
  }

  @Provides
  @Singleton
  internal fun provideAppActivitymanager(): AppActivityManager {
    return AppActivityManager()
  }

  @Provides
  @Singleton
  internal fun provideSharedPreferenceManager(): SharedPreferenceManager {
    return SharedPreferenceManager(
        application.applicationContext.getSharedPreferences(SharedPreferenceKeys.MY_PREFERENCE, Context.MODE_PRIVATE))
  }

  @Provides
  @Singleton
  fun provideFirebaseDatabaseReference(): DatabaseReference {
    return FirebaseDatabase.getInstance().reference
  }

  @Provides
  @Singleton
  fun provideFirebaseAuth(): FirebaseAuth {
    return FirebaseAuth.getInstance()
  }

  @Provides
  @Singleton
  fun provideGoogleSignInClient(): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(BuildConfig.WEB_CLIENT_ID)
        .requestProfile()
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(application, gso)
  }
}
