package com.tickr.tickr.ui

import android.app.Activity
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.tickr.tickr.R

/**
 * Created by bry1337 on 20/02/2018.
 *
 * @author edwardbryan.abergas@gmail.com
 */
abstract class BasePresenter {

  abstract fun getActivity(): Activity

  abstract fun getAlertDialog(): AlertDialog

  fun showAlertDialog(message: String) {
    getAlertDialog().setMessage(message)
    getAlertDialog().setCancelable(false)
    getAlertDialog().setButton(DialogInterface.BUTTON_POSITIVE, getActivity().getString(R.string.ok),
        { _, _ -> getAlertDialog().dismiss() })
    getAlertDialog().show()

  }
}