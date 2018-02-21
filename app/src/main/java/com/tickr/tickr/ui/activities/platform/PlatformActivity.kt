package com.tickr.tickr.ui.activities.platform

import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.tickr.tickr.R
import com.tickr.tickr.application.AppConstants
import com.tickr.tickr.application.TickrApplication
import com.tickr.tickr.managers.prefs.SharedPreferenceManager
import com.tickr.tickr.models.Article
import com.tickr.tickr.ui.HttpToolBarBaseActivity
import kotlinx.android.synthetic.main.content_no_internet.*
import kotlinx.android.synthetic.main.content_platform.*
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by bry1337 on 21/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class PlatformActivity : HttpToolBarBaseActivity() {

  @Inject
  lateinit var alertDialog: AlertDialog
  @Inject
  lateinit var subscription: CompositeSubscription
  @Inject
  lateinit var sharedPreferenceManager: SharedPreferenceManager
  @Inject
  lateinit var platformPresenter: PlatformPresenter

  private lateinit var platformNewsAdapter: PlatformNewsAdapter
  private lateinit var articles: ArrayList<Article>
  private lateinit var article: Article

  override val isActionBarBackButtonEnabled: Boolean
    get() = true

  override fun setupActivityLayout() {
    setContentView(R.layout.activity_platform)
  }

  override fun setupViewElements() {
    initPlatformNewsAdapter()
    getIntentObject()
    btnRetryConnection.setOnClickListener({
      hideNetworkErrorLayout()
      initSubscription(article)
    })
  }

  override fun injectDaggerComponent() {
    TickrApplication[this].createPlatformComponent(this).inject(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    TickrApplication[this].releasePlatformComponent()
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

  override val compositeSubscription: CompositeSubscription
    get() = subscription

  override fun onNetworkErrorFound(message: String) {
    platformPresenter.showAlertDialog(message)
  }

  override fun onHttpErrorUnexpectedFound(message: String) {
    platformPresenter.showAlertDialog(message)
  }

  fun getIntentObject() {
    article = intent.getParcelableExtra(AppConstants.ARTICLE_OBJECT)
    title = article.source?.name
    initSubscription(article)
  }

  fun initSubscription(article: Article) {
    subscription.add(platformPresenter.getTopHeadlines(article.source?.id!!))
  }

  fun initPlatformNewsAdapter() {
    articles = ArrayList()
    platformNewsAdapter = PlatformNewsAdapter(articles, this, platformPresenter)
    rvPlatformNews.adapter = platformNewsAdapter
    rvPlatformNews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    rvPlatformNews.isNestedScrollingEnabled = false
  }

  fun setArticles(articleList: ArrayList<Article>) {
    articles.clear()
    articles.addAll(articleList)
    platformNewsAdapter.notifyDataSetChanged()
  }

  fun showProgressBar() {
    pbPlatformNews.visibility = View.VISIBLE
  }

  fun hideProgressBar() {
    pbPlatformNews.visibility = View.GONE
  }

  fun showNetworkErrorLayout() {
    noInternetLayoutPlatform.visibility = View.VISIBLE
  }

  fun hideNetworkErrorLayout() {
    if (noInternetLayoutPlatform.visibility == View.VISIBLE) {
      noInternetLayoutPlatform.visibility = View.GONE
    }
  }

  fun showUnexpectedError() {
    platformPresenter.showAlertDialog(getString(R.string.http_error_unexpected))
  }
}