package com.tickr.tickr.ui.activities.login

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.tickr.tickr.R
import com.tickr.tickr.application.TickrApplication
import com.tickr.tickr.managers.AppActivityManager
import com.tickr.tickr.managers.prefs.SharedPreferenceManager
import com.tickr.tickr.ui.ToolBarbaseActivity
import kotlinx.android.synthetic.main.content_login.*
import javax.inject.Inject


/**
 * Created by bry1337 on 12/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class LoginActivity : ToolBarbaseActivity() {

  val GOOGLE_SIGN_IN_REQUEST: Int = 1000

  @Inject
  lateinit var loginPresenter: LoginPresenter
  @Inject
  lateinit var appActivityManager: AppActivityManager
  @Inject
  lateinit var alertDialog: AlertDialog
  @Inject
  lateinit var sharedPreferenceManager: SharedPreferenceManager
  @Inject
  lateinit var databaseReference: DatabaseReference
  @Inject
  lateinit var mGoogleSignInClient: GoogleSignInClient
  @Inject
  lateinit var mAuth: FirebaseAuth

  override val isActionBarBackButtonEnabled: Boolean
    get() = true

  override fun setupActivityLayout() {
    setContentView(R.layout.activity_login)
  }

  override fun setupViewElements() {
    btnGoogleSignIn.setOnClickListener({ onGoogleSignIn() })
    btnGuest.setOnClickListener({ onGuestSignIn() })
  }

  override fun injectDaggerComponent() {
    TickrApplication[this].createLoginComponent(this).inject(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    TickrApplication[this].releaseLoginComponent()
  }

  override fun onStart() {
    super.onStart()
    loginPresenter.initLastSignInAccount()
  }

  private fun onGoogleSignIn() {
    if (loginPresenter.isNetworkAvailable(this)) {
      startActivityForResult(loginPresenter.getGoogleSignInIntent(), GOOGLE_SIGN_IN_REQUEST)
    } else {
      Toast.makeText(this, getString(R.string.no_internet), LENGTH_SHORT).show()
    }
  }

  private fun onGuestSignIn() {
    loginPresenter.guestSignIn()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == GOOGLE_SIGN_IN_REQUEST) {
      if (resultCode == Activity.RESULT_OK) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        loginPresenter.handleSignInResult(task)
      }
    }
  }

}