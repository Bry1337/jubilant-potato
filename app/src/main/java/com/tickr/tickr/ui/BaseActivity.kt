package com.tickr.tickr.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.tickr.tickr.BuildConfig
import com.tickr.tickr.ui.utils.LocaleHelper

/**
 * Created by bry1337 on 25/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */

abstract class BaseActivity : AppCompatActivity() {

  private var unbinder: Unbinder? = null

  protected abstract val isActionBarBackButtonEnabled: Boolean

  protected abstract fun setupActivityLayout()

  protected abstract fun setupViewElements()

  protected abstract fun setupToolbar()

  protected abstract fun injectDaggerComponent()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupActivityLayout()
    unbinder = ButterKnife.bind(this)
    injectDaggerComponent()
    setupToolbar()
    setupViewElements()
  }

  override fun attachBaseContext(newBase: Context) {
    super.attachBaseContext(LocaleHelper.onAttach(newBase, BuildConfig.LANGUAGE))
  }

  override fun onDestroy() {
    super.onDestroy()
    unbinder!!.unbind()
  }
}
