package com.tickr.tickr.ui.activities.detailednews

import android.support.v7.app.AlertDialog
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
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
  @Inject
  lateinit var databaseReference: DatabaseReference

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

  fun showBookmarkSuccess() {
    Toast.makeText(this, getString(R.string.this_article_has_been_bookmarked), LENGTH_SHORT).show()
  }

  fun showErrorMessage() {
    presenter.showAlertDialog(getString(R.string.something_unexpected_happened))
  }

  private fun setViewObjects() {
    tvDate.text = presenter.getCurrentDateAndTimeFormat(article.publishedAt!!)
    tvAuthor.text = if (article.author != null) article.author else article.source?.name
    tvTitle.text = article.title
    tvDescription.text = article.description
    Glide.with(this).load(article.urlToImage).centerCrop().crossFade().into(ivDetailedNewsImage)
  }

  private fun setListener() {
    tvReadMore.setOnClickListener({ presenter.redirectToBrowser(article.url!!) })
    ivShareNews.setOnClickListener({ presenter.shareToOthers(article.url!!) })
    ivBookMark.setOnClickListener({
      if (sharedPreferenceManager.isUserLoggedIn()) {
        presenter.saveToFirebase(article)
      } else {
        appActivityManager.displayLoginScreen(this)
      }
    })
  }


}