package com.tickr.tickr.ui.activities.detailednews

import android.support.v7.app.AlertDialog
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.tickr.tickr.R
import com.tickr.tickr.application.AppConstants
import com.tickr.tickr.application.TickrApplication
import com.tickr.tickr.managers.AppActivityManager
import com.tickr.tickr.managers.prefs.SharedPreferenceManager
import com.tickr.tickr.models.Article
import com.tickr.tickr.ui.ToolBarbaseActivity
import kotlinx.android.synthetic.main.content_detailed_news.*
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by bry1337 on 22/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class DetailedNewsActivity : ToolBarbaseActivity() {

  @Inject
  lateinit var alertDialog: AlertDialog
  @Inject
  lateinit var subscription: CompositeSubscription
  @Inject
  lateinit var sharedPreferenceManager: SharedPreferenceManager
  @Inject
  lateinit var presenter: DetailedNewsPresenter
  @Inject
  lateinit var appActivityManager: AppActivityManager

  private lateinit var article: Article

  override val isActionBarBackButtonEnabled: Boolean
    get() = true

  override fun setupActivityLayout() {
    setContentView(R.layout.activity_detailed_news)
  }

  override fun setupViewElements() {
    getIntentObject()
    setViewObjects()
    setListener()
  }

  override fun injectDaggerComponent() {
    TickrApplication[this].createDetailedNewsComponent(this).inject(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    TickrApplication[this].releaseDetailedNewsComponent()
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
  }

  private fun setViewObjects() {
    tvDate.text = presenter.getCurrentDateAndTimeFormat(article.publishedAt!!)
    tvAuthor.text = article.author
    tvTitle.text = article.title
    tvDescription.text = article.description
    tvLink.text = article.url
    Glide.with(this).load(article.urlToImage).centerCrop().crossFade().into(ivDetailedNewsImage)
  }

  private fun setListener() {
    tvLink.setOnClickListener({ presenter.redirectToBrowser(tvLink.text as String) })
    ivShareNews.setOnClickListener({ presenter.shareToOthers(article.url!!) })
  }


}