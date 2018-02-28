package com.tickr.tickr.ui.activities.bookmark

import android.app.Activity
import android.content.ContentValues
import android.support.v7.app.AlertDialog
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tickr.tickr.api.managers.ApiManager
import com.tickr.tickr.application.AppConstants
import com.tickr.tickr.models.Article
import com.tickr.tickr.ui.BasePresenter
import com.tickr.tickr.ui.utils.OnBookmarkDeleteListener
import com.tickr.tickr.ui.utils.OnSingleItemClickListener

/**
 * Created by bry1337 on 28/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class BookmarkPresenter(var activity: BookmarkActivity,
    var apiManager: ApiManager) : BasePresenter(), OnSingleItemClickListener, OnBookmarkDeleteListener {


  override fun onBookmarkDelete(obj: Any) {
    val article = obj as Article
    activity.databaseReference.child(AppConstants.FIREBASE_BOOKMARKS).child(
        activity.sharedPreferenceManager.getUID()).child(article.firebaseKey).removeValue()

  }

  private var articleList: ArrayList<Article> = ArrayList()

  override fun onSingleItemClick(obj: Any) {
    val article: Article = obj as Article
    activity.appActivityManager.displayDetailedNews(activity, article)
  }

  override fun getActivity(): Activity {
    return activity
  }

  override fun getAlertDialog(): AlertDialog {
    return activity.alertDialog
  }

  fun initBookmarks() {
    activity.showProgressbar()
    activity.databaseReference.child(
        AppConstants.FIREBASE_BOOKMARKS).child(
        activity.sharedPreferenceManager.getUID()).addValueEventListener(object : ValueEventListener {
      override fun onCancelled(p0: DatabaseError?) {
        Log.w(ContentValues.TAG, "loadPost:onCancelled", p0?.toException())
      }

      override fun onDataChange(p0: DataSnapshot?) {
        processFirebaseOnDataChange(p0)
      }

    })
  }


  private fun processFirebaseOnDataChange(p0: DataSnapshot?) {
    articleList.clear()
    for (dataSnapShot: DataSnapshot in p0?.children!!) run {
      val key = dataSnapShot.key
      val article = dataSnapShot.getValue(Article::class.java)
      if (article != null) {
        article.firebaseKey = key.toString()
        articleList.add(article)
      }
    }

    if (articleList.size > 0) {
      activity.setArticles(articleList)
    }
    activity.hideProgressbar()
  }
}