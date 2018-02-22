package com.tickr.tickr.ui.activities.home

import android.content.DialogInterface
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.tickr.tickr.R
import com.tickr.tickr.application.TickrApplication
import com.tickr.tickr.managers.AppActivityManager
import com.tickr.tickr.managers.prefs.SharedPreferenceManager
import com.tickr.tickr.models.Article
import com.tickr.tickr.ui.HttpToolBarBaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home_drawer.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.content_no_internet.*
import kotlinx.android.synthetic.main.content_profile_section.*
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

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
  lateinit var mGoogleSignInClient: GoogleSignInClient
  @Inject
  lateinit var mAuth: FirebaseAuth

  private lateinit var homeDefaultCategoryAdapter: HomeDefaultCategoryAdapter
  private lateinit var articles: ArrayList<Article>


  override val isActionBarBackButtonEnabled: Boolean
    get() = false

  override fun setupActivityLayout() {
    setContentView(R.layout.activity_home_drawer)
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
    }
    return super.onOptionsItemSelected(item)
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

  fun showUnexpectedError() {
    homePresenter.showAlertDialog(getString(R.string.http_error_unexpected))
  }

  fun setViewObjects() {
    tvFullName.text = sharedPreferenceManager.getFullName()
    Glide.with(this).load(sharedPreferenceManager.getPhotoUri()).centerCrop().crossFade().into(ivProfilePicture)
    if (!sharedPreferenceManager.isUserLoggedIn()) {
      ivLogout.visibility = View.GONE
    } else {
      setupNavigation()
    }
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
    val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open,
        R.string.navigation_drawer_close)
    drawer_layout.addDrawerListener(toggle)
    toggle.syncState()
  }


  private fun initListener() {
    btnRetryConnection.setOnClickListener({
      hideNetworkErrorLayout()
      homePresenter.initNewsDefaultCategory()
    })
    ivLogout.setOnClickListener({
      logoutApp()
    })
  }


}