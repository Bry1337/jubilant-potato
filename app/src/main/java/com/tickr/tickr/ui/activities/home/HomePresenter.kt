package com.tickr.tickr.ui.activities.home

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AlertDialog
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tickr.tickr.api.managers.ApiManager
import com.tickr.tickr.api.response.NewsResponse
import com.tickr.tickr.api.utils.RetrofitException
import com.tickr.tickr.api.utils.SimpleObserver
import com.tickr.tickr.application.AppConstants
import com.tickr.tickr.models.Article
import com.tickr.tickr.models.Category
import com.tickr.tickr.ui.BasePresenter
import rx.Subscription


/**
 * Created by bry1337 on 20/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class HomePresenter(var activity: HomeActivity, var apiManager: ApiManager) : BasePresenter() {

  lateinit var articleList: ArrayList<Article>
  var categorySize: Int = 0
  var totalCategorySize: Int = 0

  override fun getAlertDialog(): AlertDialog {
    return activity.alertDialog
  }

  override fun getActivity(): Activity {
    return activity
  }

  fun initArticleList() {
    articleList = ArrayList()
  }

  fun initNewsDefaultCategory() {
    activity.databaseReference.addValueEventListener(object : ValueEventListener {
      override fun onCancelled(p0: DatabaseError?) {
        Log.w(TAG, "loadPost:onCancelled", p0?.toException())
      }

      override fun onDataChange(p0: DataSnapshot?) {
        processFirebaseOnDataChange(p0)
      }

    })
    if (!isNetworkAvailable(activity)) {
      activity.showNetworkErrorLayout()
    } else {
      activity.hideNetworkErrorLayout()
    }
  }

  private fun processFirebaseOnDataChange(p0: DataSnapshot?) {
    categorySize = p0?.child(AppConstants.FIREBASE_CATEGORY)!!.child(
        AppConstants.FIREBASE_DEFAULT_CATEGORY)!!.childrenCount.toInt()
    for (dataSnapShot: DataSnapshot in p0.child(AppConstants.FIREBASE_CATEGORY)!!.child(
        AppConstants.FIREBASE_DEFAULT_CATEGORY)!!.children) run {
      val category = dataSnapShot.getValue(Category::class.java)
      getCategoryTopHeadlines(category!!.source)
      totalCategorySize++
    }
  }


  fun getCategoryTopHeadlines(sources: String): Subscription {
    activity.showProgressBar()
    return apiManager.getCategoryTopHeadlines(sources).subscribe(object : SimpleObserver<NewsResponse>() {
      override fun onNext(t: NewsResponse) {
        activity.hideProgressBar()
        processAddingArticleList(t)
      }

      override fun onError(e: Throwable?) {
        activity.hideProgressBar()
        if ((e as RetrofitException).kind?.equals(RetrofitException.Kind.NETWORK)!!) {
          activity.showNetworkErrorLayout()
        }
      }

    })
  }

  fun processAddingArticleList(newsResponse: NewsResponse) {
    articleList.add(newsResponse.articles!![0])
    activity.setArticles(articleList)
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

