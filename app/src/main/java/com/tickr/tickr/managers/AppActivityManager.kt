package com.tickr.tickr.managers

import android.app.Activity
import android.content.Intent
import com.tickr.tickr.ui.activities.login.LoginActivity

/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class AppActivityManager {

  fun displayLoginScreen(activity: Activity) {
    val intent: Intent = Intent(activity, LoginActivity::class.java)
    activity.startActivity(intent)
  }
}