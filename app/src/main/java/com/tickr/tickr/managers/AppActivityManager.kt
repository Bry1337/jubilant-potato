package com.tickr.tickr.managers

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.net.Uri
import com.tickr.tickr.application.AppConstants
import com.tickr.tickr.models.Article
import com.tickr.tickr.ui.activities.bookmark.BookmarkActivity
import com.tickr.tickr.ui.activities.detailednews.DetailedNewsActivity
import com.tickr.tickr.ui.activities.home.HomeActivity
import com.tickr.tickr.ui.activities.newslist.PlatformListActivity
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

  fun displayPlatformNews(activity: Activity, article: Article) {
    val intent = Intent(activity, PlatformActivity::class.java)
    intent.putExtra(AppConstants.ARTICLE_OBJECT, article)
    activity.startActivity(intent)
  }

  fun displayDetailedNews(activity: Activity, article: Article) {
    val intent = Intent(activity, DetailedNewsActivity::class.java)
    intent.putExtra(AppConstants.ARTICLE_OBJECT, article)
    activity.startActivity(intent)
  }

  fun redirectToChrome(activity: Activity, url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    intent.flags = FLAG_GRANT_READ_URI_PERMISSION
    intent.addCategory(Intent.CATEGORY_BROWSABLE)
    activity.startActivity(intent)
  }

  fun shareToOthers(activity: Activity, url: String) {
    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.type = AppConstants.SHARING_TYPE
    intent.putExtra(Intent.EXTRA_TEXT, url)
    activity.startActivity(Intent.createChooser(intent, AppConstants.SHARE_TEXT))
  }

  fun displayPlatformListNews(activity: Activity, article: Article) {
    val intent = Intent(activity, PlatformListActivity::class.java)
    intent.putExtra(AppConstants.ARTICLE_OBJECT, article)
    activity.startActivity(intent)
  }

  fun displayBookmarks(activity: Activity) {
    val intent = Intent(activity, BookmarkActivity::class.java)
    activity.startActivity(intent)
  }
}