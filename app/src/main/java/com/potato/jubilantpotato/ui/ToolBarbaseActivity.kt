package com.potato.jubilantpotato.ui

import android.widget.Toolbar
import butterknife.BindView
import com.potato.jubilantpotato.R

/**
 * Created by bry1337 on 25/01/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */

abstract class ToolBarbaseActivity : BaseActivity() {

  @BindView(R.id.toolbar)
  lateinit var toolbar: Toolbar
}
