package com.tickr.tickr.ui.activities.detailednews

import android.app.Activity
import android.support.v7.app.AlertDialog
import com.tickr.tickr.api.managers.ApiManager
import com.tickr.tickr.application.AppConstants
import com.tickr.tickr.models.Article
import com.tickr.tickr.ui.BasePresenter
import com.tickr.tickr.ui.utils.Base64Utility
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by bry1337 on 22/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class DetailedNewsPresenter(var activity: DetailedNewsActivity,
    var apiManager: ApiManager) : BasePresenter() {

  override fun getActivity(): Activity {
    return activity
  }

  override fun getAlertDialog(): AlertDialog {
    return activity.alertDialog
  }

  fun getCurrentDateAndTimeFormat(publishedAt: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val outputFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US)
    return try {
      val date = dateFormat.parse(publishedAt)
      outputFormat.format(date)
    } catch (e: ParseException) {
      e.printStackTrace()
      val dateFormat2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
      val date = dateFormat2.parse(publishedAt)
      outputFormat.format(date)
    }
  }

  fun redirectToBrowser(url: String) {
    activity.appActivityManager.redirectToChrome(activity, url)
  }

  fun shareToOthers(url: String) {
    activity.appActivityManager.shareToOthers(activity, url)
  }

  fun saveToFirebase(article: Article) {
    val sourceId = Base64Utility.encodeTextWithBase64(
        String.format("%s%s%s", article.source?.id, article.author, article.publishedAt))
    val databaseReference = activity.databaseReference.child(AppConstants.FIREBASE_BOOKMARKS).child(
        activity.sharedPreferenceManager.getUID())
    databaseReference.child(sourceId).setValue(article).addOnCompleteListener({ task ->
      if (task.isSuccessful) {
        activity.showBookmarkSuccess()
      }
    })

  }

}