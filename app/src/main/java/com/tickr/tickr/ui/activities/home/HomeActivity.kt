package com.tickr.tickr.ui.activities.home

import android.content.DialogInterface
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.tickr.tickr.application.TickrApplication
import com.tickr.tickr.managers.AppActivityManager
import com.tickr.tickr.managers.prefs.SharedPreferenceManager
import com.tickr.tickr.models.Article
import com.tickr.tickr.ui.HttpToolBarBaseActivity
import kotlinx.android.synthetic.main.activity_home_drawer.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.content_no_internet.*
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject
import android.content.Intent
import android.net.Uri
import com.tickr.tickr.R
import com.tickr.tickr.application.AppConstants


/**
 * Created by bry1337 on 20/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class HomeActivity : HttpToolBarBaseActivity() {

  @Inject
  lateinit var alertDialog: AlertDialog
  @Inject
  lateinit var appActivityManager: AppActivityManager
  @Inject
  lateinit var databaseReference: DatabaseReference
  @Inject
  lateinit var homePresenter: HomePresenter
  @Inject
  lateinit var subscription: CompositeSubscription
  @Inject
  lateinit var sharedPreferenceManager: SharedPreferenceManager
  @Inject
  lateinit var mAuth: FirebaseAuth

  private lateinit var homeDefaultCategoryAdapter: HomeDefaultCategoryAdapter
  private lateinit var articles: ArrayList<Article>


  override val isActionBarBackButtonEnabled: Boolean
    get() = false

  override fun setupActivityLayout() {
    setContentView(com.tickr.tickr.R.layout.activity_home)
  }

  override fun setupViewElements() {
    initHomeDefaultCategoryAdapter()
    setViewObjects()
    homePresenter.initArticleList()
    homePresenter.initNewsDefaultCategory()
    initListener()
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

      R.id.action_privacy_policy -> {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(AppConstants.PRIVACY_POLICY)
        startActivity(intent)
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    if (!sharedPreferenceManager.isUserLoggedIn()) {
      val menuInflater: MenuInflater = menuInflater
      menuInflater.inflate(R.menu.home, menu)
    }
    return super.onCreateOptionsMenu(menu)
  }


  override fun onBackPressed() {
    if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
      drawer_layout.closeDrawer(GravityCompat.START)
    } else {
      exitApp()
    }
  }

  override val compositeSubscription: CompositeSubscription
    get() = subscription

  override fun onNetworkErrorFound(message: String) {
    homePresenter.showAlertDialog(message)
  }

  override fun onHttpErrorUnexpectedFound(message: String) {
    homePresenter.showAlertDialog(message)
  }

  fun initHomeDefaultCategoryAdapter() {
    articles = ArrayList()
    homeDefaultCategoryAdapter = HomeDefaultCategoryAdapter(articles, homePresenter, this)
    rvDefaultCategory.adapter = homeDefaultCategoryAdapter
    rvDefaultCategory.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
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

  fun showUnexpectedError() {
    homePresenter.showAlertDialog(getString(R.string.http_error_unexpected))
  }

  fun setViewObjects() {
    setupNavigation()
  }

  fun exitApp() {
    val dialogBuilder = AlertDialog.Builder(this, R.style.ExitDialog)
    val exitDialog: AlertDialog = dialogBuilder.create()
    exitDialog.setMessage(getString(R.string.are_you_sure_you_want_to_exit))
    exitDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.yes)
    ) { _, _ ->
      exitDialog.dismiss()
      finish()
    }
    exitDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.no)
    ) { _, _ -> alertDialog.dismiss() }
    exitDialog.show()
  }

  fun logoutApp() {
    val dialogBuilder = AlertDialog.Builder(this, R.style.ExitDialog)
    val exitDialog: AlertDialog = dialogBuilder.create()
    exitDialog.setMessage(getString(R.string.are_you_sure_you_want_to_logout))
    exitDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.yes)
    ) { _, _ ->
      homePresenter.signOut()
    }
    exitDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.no)
    ) { _, _ -> alertDialog.dismiss() }
    exitDialog.show()
  }


  private fun setupNavigation() {
//    val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string
//        .navigation_drawer_open,
//        R.string.navigation_drawer_close)
//    drawer_layout.addDrawerListener(toggle)
//    toggle.syncState()
  }


  private fun initListener() {
    btnRetryConnection.setOnClickListener {
      hideNetworkErrorLayout()
      homePresenter.initNewsDefaultCategory()
    }
//    ivLogout.setOnClickListener {
//      logoutApp()
//    }
//    llBookMarks.setOnClickListener {
//      appActivityManager.displayBookmarks(this)
//    }
  }


}