package com.tickr.tickr.ui

import android.support.v7.widget.Toolbar
import com.tickr.tickr.R

/**
 * Created by bry1337 on 25/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */

abstract class ToolBarbaseActivity : BaseActivity() {

  private var toolbar: Toolbar? = null

  override fun setupToolbar() {
    toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)
    if (isActionBarBackButtonEnabled) {
      supportActionBar?.setDisplayHomeAsUpEnabled(true)
      supportActionBar?.setDisplayShowHomeEnabled(true)
    }
  }
}
