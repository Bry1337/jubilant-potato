package com.tickr.tickr.ui.activities.home

import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import android.view.View
import com.google.firebase.database.DatabaseReference
import com.tickr.tickr.R
import com.tickr.tickr.application.TickrApplication
import com.tickr.tickr.managers.AppActivityManager
import com.tickr.tickr.models.Article
import com.tickr.tickr.ui.ToolBarbaseActivity
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.content_no_internet.*
import javax.inject.Inject

/**
 * Created by bry1337 on 20/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class HomeActivity : ToolBarbaseActivity() {

  @Inject
  lateinit var alertDialog: AlertDialog
  @Inject
  lateinit var appActivityManager: AppActivityManager
  @Inject
  lateinit var databaseReference: DatabaseReference
  @Inject
  lateinit var homePresenter: HomePresenter

  private lateinit var homeDefaultCategoryAdapter: HomeDefaultCategoryAdapter
  private lateinit var articles: ArrayList<Article>


  override val isActionBarBackButtonEnabled: Boolean
    get() = true

  override fun setupActivityLayout() {
    setContentView(R.layout.activity_home)
  }

  override fun setupViewElements() {
    initHomeDefaultCategoryAdapter()
    homePresenter.initArticleList()
    homePresenter.initNewsDefaultCategory()
    btnRetryConnection.setOnClickListener({
      hideNetworkErrorLayout()
      homePresenter.initNewsDefaultCategory()
    })
  }

  override fun injectDaggerComponent() {
    TickrApplication[this].createHomeComponent(this).inject(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    TickrApplication[this].releaseHomeComponent()
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

  fun initHomeDefaultCategoryAdapter() {
    articles = ArrayList()
    homeDefaultCategoryAdapter = HomeDefaultCategoryAdapter(articles, homePresenter, this)
    rvDefaultCategory.adapter = homeDefaultCategoryAdapter
    rvDefaultCategory.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
    rvDefaultCategory.isNestedScrollingEnabled = false
  }

  fun setArticles(articleList: ArrayList<Article>) {
    articles.clear()
    articles.addAll(articleList)
    homeDefaultCategoryAdapter.notifyDataSetChanged()
  }

  fun showProgressBar() {
    pbHome.visibility = View.VISIBLE
  }

  fun hideProgressBar() {
    pbHome.visibility = View.GONE
  }

  fun showNetworkErrorLayout() {
    noInternetLayout.visibility = View.VISIBLE
  }

  fun hideNetworkErrorLayout() {
    if (noInternetLayout.visibility == View.VISIBLE) {
      noInternetLayout.visibility = View.GONE
    }
  }

}