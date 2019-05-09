package com.tickr.tickr.ui.activities.home

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.appcompat.app.AlertDialog
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
import com.tickr.tickr.ui.utils.OnSingleItemClickListener
import rx.Subscription


/**
 * Created by bry1337 on 20/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class HomePresenter(var activity: HomeActivity,
    var apiManager: ApiManager) : BasePresenter(), OnSingleItemClickListener {

  lateinit var articleList: ArrayList<Article>

  override fun getAlertDialog(): AlertDialog {
    return activity.alertDialog
  }

  override fun getActivity(): Activity {
    return activity
  }

  override fun onSingleItemClick(obj: Any) {
    val article: Article = obj as Article
    activity.appActivityManager.displayPlatformNews(activity, article)
  }


  fun initArticleList() {
    articleList = ArrayList()
  }

  fun initNewsDefaultCategory() {
    activity.databaseReference.child(
        AppConstants.FIREBASE_CATEGORY).addValueEventListener(object : ValueEventListener {
      override fun onCancelled(p0: DatabaseError) {
        Log.w(TAG, "loadPost:onCancelled", p0?.toException())
      }

      override fun onDataChange(p0: DataSnapshot) {
        processFirebaseOnDataChange(p0)
      }

    })
    handleNetworkLayout()
  }

  fun processAddingArticleList(newsResponse: NewsResponse) {
    if (articleList.size == AppConstants.CATEGORY_CURRENT_COUNT) {
      articleList.clear()
    }
    articleList.add(newsResponse.articles!![0])
    activity.setArticles(articleList)
  }

  fun signOut() {
    activity.mAuth.signOut()
    activity.sharedPreferenceManager.clearSharedPreferences()
    activity.finish()
  }


  private fun processFirebaseOnDataChange(p0: DataSnapshot?) {
    if (articleList.size > 0) {
      articleList.clear()
    }
    activity.showProgressBar()
    for (dataSnapShot: DataSnapshot in p0?.child(
        AppConstants.FIREBASE_DEFAULT_CATEGORY)!!.children) run {
      val category = dataSnapShot.getValue(Category::class.java)
      activity.subscription.add(getCategoryTopHeadlines(category!!.source))
    }
    handleNetworkLayout()
  }


  private fun getCategoryTopHeadlines(sources: String): Subscription {
    return apiManager.getCategoryTopHeadlines(
        sources).subscribe(object : SimpleObserver<NewsResponse>() {
      override fun onNext(t: NewsResponse) {
        activity.hideProgressBar()
        if (t.status.equals(AppConstants.NEWS_API_STATUS_OK)) {
          processAddingArticleList(t)
        } else {
          activity.showUnexpectedError()
        }
      }

      override fun onError(e: Throwable?) {
        activity.hideProgressBar()
        if ((e as RetrofitException).kind?.equals(RetrofitException.Kind.NETWORK)!!) {
          activity.showNetworkErrorLayout()
        } else {
          activity.handleHttpError(e, activity.sharedPreferenceManager)
        }
      }

    })
  }


  private fun isNetworkAvailable(con: Context): Boolean {
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

  private fun handleNetworkLayout() {
    if (!isNetworkAvailable(activity)) {
      activity.showNetworkErrorLayout()
    } else {
      activity.hideNetworkErrorLayout()
    }
  }

}

