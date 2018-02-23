package com.tickr.tickr.ui.activities.login

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AlertDialog
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.tickr.tickr.R
import com.tickr.tickr.api.managers.ApiManager
import com.tickr.tickr.ui.BasePresenter


/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class LoginPresenter(var activity: LoginActivity, var apiManager: ApiManager) : BasePresenter() {

  lateinit var currentUser: FirebaseUser

  override fun getActivity(): Activity {
    return activity
  }

  override fun getAlertDialog(): AlertDialog {
    return activity.alertDialog
  }


  fun initLastSignInAccount() {
    if (checkGoogleLastSignInAccount() != null) {
      currentUser = activity.mAuth.currentUser!!
      activity.appActivityManager.displayHomeScreen(activity)
    }
  }

  fun checkGoogleLastSignInAccount(): GoogleSignInAccount? {
    return GoogleSignIn.getLastSignedInAccount(activity)
  }

  fun getGoogleSignInIntent(): Intent {
    return activity.mGoogleSignInClient.signInIntent
  }

  fun guestSignIn() {
    activity.appActivityManager.displayHomeScreen(activity)
    activity.finish()
  }

  fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
    try {
      val account = completedTask.getResult(ApiException::class.java)

      // Signed in successfully, show authenticated UI.
      firebaseAuthWithGoogle(account)
      activity.sharedPreferenceManager.logUserIn()
      activity.sharedPreferenceManager.saveFullName(account.displayName.toString())
      activity.sharedPreferenceManager.savePhotoUri(account.photoUrl.toString())
      activity.appActivityManager.displayHomeScreen(activity)
      activity.finish()
    } catch (e: ApiException) {
      // The ApiException status code indicates the detailed failure reason.
      // Please refer to the GoogleSignInStatusCodes class reference for more information.
      Log.w(TAG, "signInResult:failed code=" + e.statusCode)
    }

  }

  private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
    Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
    val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
    activity.mAuth.signInWithCredential(credential)
        .addOnCompleteListener(activity, OnCompleteListener<AuthResult> { task ->
          if (task.isSuccessful) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInWithCredential:success")
            val user = activity.mAuth.currentUser
            activity.sharedPreferenceManager.logUserIn()
          } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInWithCredential:failure", task.exception)
            showAlertDialog(activity.getString(R.string.authentication_failed))
          }

        })
  }

  fun isNetworkAvailable(con: Context): Boolean {
    try {
      val cm = con
          .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val networkInfo = cm.activeNetworkInfo

      if (networkInfo != null && networkInfo.isConnected) {
        return true
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }

    return false
  }


}