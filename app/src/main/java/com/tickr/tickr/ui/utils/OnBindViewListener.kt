package com.tickr.tickr.ui.utils

import android.app.Activity

/**
 * Created by bry1337 on 21/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
interface OnBindViewListener {

  fun onBind(obj: Any, activity: Activity, presenter: Any)
}