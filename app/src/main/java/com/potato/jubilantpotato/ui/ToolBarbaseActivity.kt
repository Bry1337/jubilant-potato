package com.potato.jubilantpotato.ui

import android.support.v7.widget.Toolbar
import butterknife.BindView
import com.potato.jubilantpotato.R

/**
 * Created by bry1337 on 25/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */

abstract class ToolBarbaseActivity : BaseActivity() {

  @BindView(R.id.toolbar)
  var toolbar: Toolbar? = null

  override fun setupToolbar() {
    setSupportActionBar(toolbar)
    if (isActionBarBackButtonEnabled) {
      supportActionBar?.setDisplayHomeAsUpEnabled(true)
      supportActionBar?.setDisplayShowHomeEnabled(true)
    }
  }
}
