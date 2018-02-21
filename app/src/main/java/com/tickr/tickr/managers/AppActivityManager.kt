package com.tickr.tickr.managers

import android.app.Activity
import android.content.Intent
import com.tickr.tickr.application.AppConstants
import com.tickr.tickr.models.Article
import com.tickr.tickr.ui.activities.home.HomeActivity
import com.tickr.tickr.ui.activities.platform.PlatformActivity

/**
 * Created by bry1337 on 19/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class AppActivityManager {

  fun displayHomeScreen(activity: Activity) {
    val intent = Intent(activity, HomeActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    activity.startActivity(intent)
  }

  fun displayPlatformnews(activity: Activity, article: Article) {
    val intent = Intent(activity, PlatformActivity::class.java)
    intent.putExtra(AppConstants.ARTICLE_OBJECT, article)
    activity.startActivity(intent)
  }
}