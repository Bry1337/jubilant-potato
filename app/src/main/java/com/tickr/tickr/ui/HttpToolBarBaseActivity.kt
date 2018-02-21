package com.tickr.tickr.ui

import com.tickr.tickr.R
import com.tickr.tickr.api.utils.HttpStatusCode
import com.tickr.tickr.api.utils.RetrofitException
import com.tickr.tickr.managers.prefs.SharedPreferenceManager
import rx.subscriptions.CompositeSubscription

/**
 * Created by ceosilvajr on 11/23/16.
 *
 * @author ceosilvajr@gmail.com
 */
abstract class HttpToolBarBaseActivity : ToolBarbaseActivity() {

  abstract val compositeSubscription: CompositeSubscription

  abstract fun onNetworkErrorFound(message: String)

  abstract fun onHttpErrorUnexpectedFound(message: String)

  fun handleHttpError(throwable: Throwable, sharedPreferenceManager: SharedPreferenceManager) {
    val e = throwable as RetrofitException
    if (e.kind == RetrofitException.Kind.HTTP) {
      val response = e.response
      if (response != null) {
        if (response.code() == HttpStatusCode.UNAUTHORIZED) {
          // TODO (bryan) handle unauthorized
        } else {
          onHttpErrorUnexpectedFound(response.message())
        }
      }
    } else if (e.kind == RetrofitException.Kind.UNEXPECTED) {
      onHttpErrorUnexpectedFound(getString(R.string.http_error_unexpected))
    }
  }


  override fun onDestroy() {
    super.onDestroy()
    if (compositeSubscription.hasSubscriptions()) {
      compositeSubscription.unsubscribe()
    }
  }
}