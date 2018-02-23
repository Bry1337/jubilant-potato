package com.tickr.tickr.ui.activities.newslist

import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.tickr.tickr.R
import com.tickr.tickr.application.AppConstants
import com.tickr.tickr.application.TickrApplication
import com.tickr.tickr.managers.AppActivityManager
import com.tickr.tickr.managers.prefs.SharedPreferenceManager
import com.tickr.tickr.models.Article
import com.tickr.tickr.ui.HttpToolBarBaseActivity
import kotlinx.android.synthetic.main.content_platform_list.*
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by bry1337 on 23/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class PlatformListActivity : HttpToolBarBaseActivity() {

  @Inject
  lateinit var alertDialog: AlertDialog
  @Inject
  lateinit var subscription: CompositeSubscription
  @Inject
  lateinit var sharedPreferenceManager: SharedPreferenceManager
  @Inject
  lateinit var appActivityManager: AppActivityManager
  @Inject
  lateinit var platformPresenter: PlatformListPresenter

  private lateinit var platformListAdapter: PlatformListAdapter
  private lateinit var articles: ArrayList<Article>
  private lateinit var article: Article

  override val compositeSubscription: CompositeSubscription
    get() = subscription
  override val isActionBarBackButtonEnabled: Boolean
    get() = true

  override fun onNetworkErrorFound(message: String) {
    platformPresenter.showAlertDialog(message)
  }

  override fun onHttpErrorUnexpectedFound(message: String) {
    platformPresenter.showAlertDialog(message)
  }

  override fun setupActivityLayout() {
    setContentView(R.layout.activity_platform_list)
  }

  override fun setupViewElements() {
    initPlatformNewsAdapter()
    getIntentObject()
  }

  override fun injectDaggerComponent() {
    TickrApplication[this].createPlatformListComponent(this).inject(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    TickrApplication[this].releasePlatformListComponent()
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> {
        onBackPressed()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  fun getIntentObject() {
    article = intent.getParcelableExtra(AppConstants.ARTICLE_OBJECT)
    title = article.source?.name
    initSubscription(article)
  }

  fun initSubscription(article: Article) {
    subscription.add(platformPresenter.getEverything(article.source?.id!!))
  }

  fun initPlatformNewsAdapter() {
    articles = ArrayList()
    platformListAdapter = PlatformListAdapter(articles, this, platformPresenter)
    rvPlatformListNews.adapter = platformListAdapter
    rvPlatformListNews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    rvPlatformListNews.isNestedScrollingEnabled = false
  }

  fun setArticles(articleList: ArrayList<Article>) {
    articles.clear()
    articles.addAll(articleList)
    platformListAdapter.notifyDataSetChanged()
  }

  fun showProgressBar() {
    pbPlatformListNews.visibility = View.VISIBLE
  }

  fun hideProgressBar() {
    pbPlatformListNews.visibility = View.GONE
  }

  fun showNetworkErrorLayout() {
    noInternetLayoutPlatformListNews.visibility = View.VISIBLE
  }

  fun hideNetworkErrorLayout() {
    if (noInternetLayoutPlatformListNews.visibility == View.VISIBLE) {
      noInternetLayoutPlatformListNews.visibility = View.GONE
    }
  }

  fun showUnexpectedError() {
    platformPresenter.showAlertDialog(getString(R.string.http_error_unexpected))
  }
}