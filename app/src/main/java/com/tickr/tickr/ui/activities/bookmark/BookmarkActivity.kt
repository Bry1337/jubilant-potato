package com.tickr.tickr.ui.activities.bookmark

import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import android.view.View
import com.google.firebase.database.DatabaseReference
import com.tickr.tickr.R
import com.tickr.tickr.application.TickrApplication
import com.tickr.tickr.managers.AppActivityManager
import com.tickr.tickr.managers.prefs.SharedPreferenceManager
import com.tickr.tickr.models.Article
import com.tickr.tickr.ui.HttpToolBarBaseActivity
import com.tickr.tickr.ui.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.content_bookmarks.*
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by bry1337 on 27/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class BookmarkActivity : HttpToolBarBaseActivity() {


  @Inject
  lateinit var alertDialog: AlertDialog
  @Inject
  lateinit var appActivityManager: AppActivityManager
  @Inject
  lateinit var databaseReference: DatabaseReference
  @Inject
  lateinit var subscription: CompositeSubscription
  @Inject
  lateinit var sharedPreferenceManager: SharedPreferenceManager
  @Inject
  lateinit var bookmarkPresenter: BookmarkPresenter

  private lateinit var bookmarkAdapter: BookmarkAdapter
  private lateinit var articles: ArrayList<Article>

  override val compositeSubscription: CompositeSubscription
    get() = subscription
  override val isActionBarBackButtonEnabled: Boolean
    get() = true

  override fun onNetworkErrorFound(message: String) {
    bookmarkPresenter.showAlertDialog(message)
  }

  override fun onHttpErrorUnexpectedFound(message: String) {
    bookmarkPresenter.showAlertDialog(message)
  }

  override fun setupActivityLayout() {
    setContentView(R.layout.activity_bookmarks)
  }

  override fun setupViewElements() {
    bookmarkPresenter.initBookmarks()
    initBookmarkAdapter()
  }

  override fun injectDaggerComponent() {
    TickrApplication[this].createBookmarkComponent(this).inject(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    TickrApplication[this].releaseBookmarkComponent()
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

  private fun initBookmarkAdapter() {
    articles = ArrayList()
    bookmarkAdapter = BookmarkAdapter(articles, this, bookmarkPresenter)
    rvBookmarkList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    rvBookmarkList.adapter = bookmarkAdapter
    rvBookmarkList.isNestedScrollingEnabled = false
    val swipeHandler = object : SwipeToDeleteCallback(this) {

      override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        val adapter = rvBookmarkList.adapter as BookmarkAdapter
        adapter.removeAt(viewHolder?.adapterPosition!!)
      }
    }
    val itemTouchHelper = ItemTouchHelper(swipeHandler)
    itemTouchHelper.attachToRecyclerView(rvBookmarkList)

  }

  fun setArticles(articleList: ArrayList<Article>) {
    articles.clear()
    articles.addAll(articleList)
    bookmarkAdapter.notifyDataSetChanged()
  }

  fun showProgressbar() {
    pbBookmarkList.visibility = View.VISIBLE
  }

  fun hideProgressbar() {
    pbBookmarkList.visibility = View.GONE
  }

}