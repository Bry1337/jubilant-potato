package com.potato.jubilantpotato.ui.activities

import android.content.Intent
import com.potato.jubilantpotato.R
import com.potato.jubilantpotato.ui.ToolBarbaseActivity
import com.potato.jubilantpotato.ui.activities.login.LoginActivity

class MainActivity : ToolBarbaseActivity() {
  override val isActionBarBackButtonEnabled: Boolean
    get() = false

  override fun setupActivityLayout() {
    setContentView(R.layout.activity_main)
  }

  override fun setupViewElements() {
    launchLoginScreen()
  }

  override fun injectDaggerComponent() {
  }

  private fun launchLoginScreen() {
    startActivity(Intent(this, LoginActivity::class.java))
    finish()
  }

}
