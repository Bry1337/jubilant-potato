package com.tickr.tickr.ui.activities.newslist

import android.app.Activity
import android.support.v7.app.AlertDialog
import com.tickr.tickr.api.managers.ApiManager
import com.tickr.tickr.api.response.NewsResponse
import com.tickr.tickr.api.utils.RetrofitException
import com.tickr.tickr.api.utils.SimpleObserver
import com.tickr.tickr.application.AppConstants
import com.tickr.tickr.models.Article
import com.tickr.tickr.ui.BasePresenter
import com.tickr.tickr.ui.utils.OnSingleItemClickListener
import rx.Subscription

/**
 * Created by bry1337 on 23/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class PlatformListPresenter(var activity: PlatformListActivity,
    var apiManager: ApiManager) : BasePresenter(), OnSingleItemClickListener {

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

  fun getEverything(sources: String): Subscription {
    activity.showProgressBar()
    return apiManager.getEverythingFromCategory(sources).subscribe(object : SimpleObserver<NewsResponse>() {
      override fun onNext(t: NewsResponse) {
        activity.hideProgressBar()
        if (t.status.equals(AppConstants.NEWS_API_STATUS_OK)) {
          activity.setArticles(t.articles!!)
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

}