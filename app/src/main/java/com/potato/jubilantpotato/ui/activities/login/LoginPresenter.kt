package com.potato.jubilantpotato.ui.activities.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.potato.jubilantpotato.api.managers.ApiManager


/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class LoginPresenter(var activity: LoginActivity, var apiManager: ApiManager) {

  lateinit var mGoogleSignInClient: GoogleSignInClient

  fun initGoogleSignInOptions() {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
    // Build a GoogleSignInClient with the options specified by gso.
    mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
  }

  fun initLastSignInAccount() {
    if (checkGoogleLastSignInAccount() != null) {
      // TODO proceed to news activity
    }
  }

  fun checkGoogleLastSignInAccount(): GoogleSignInAccount? {
    return GoogleSignIn.getLastSignedInAccount(activity)
  }

  fun getGoogleSignInIntent(): Intent {
    return mGoogleSignInClient.signInIntent
  }

  fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
    try {
      val account = completedTask.getResult(ApiException::class.java)

      // Signed in successfully, show authenticated UI.
      // TODO proceed to news Activity
    } catch (e: ApiException) {
      // The ApiException status code indicates the detailed failure reason.
      // Please refer to the GoogleSignInStatusCodes class reference for more information.
      Log.w(TAG, "signInResult:failed code=" + e.statusCode)
    }

  }


}