package com.tickr.tickr.ui.activities.login

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.tickr.tickr.R
import com.tickr.tickr.application.JubilantPotatoApplication
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

  override val isActionBarBackButtonEnabled: Boolean
    get() = true

  override fun setupActivityLayout() {
    setContentView(R.layout.activity_login)
  }

  override fun setupViewElements() {
    loginPresenter.initGoogleSignInOptions()
    btnGoogleSignIn.setOnClickListener({ onGoogleSignIn() })
  }

  override fun injectDaggerComponent() {
    JubilantPotatoApplication[this].createLoginComponent(this).inject(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    JubilantPotatoApplication[this].releaseLoginComponent()
  }

  override fun onStart() {
    super.onStart()
    loginPresenter.initLastSignInAccount()
  }

  private fun onGoogleSignIn() {
    startActivityForResult(loginPresenter.getGoogleSignInIntent(), GOOGLE_SIGN_IN_REQUEST)
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