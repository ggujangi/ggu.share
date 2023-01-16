package com.ggu.share.utils.dialog

import android.content.Context
import android.content.DialogInterface.OnClickListener
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import com.ggu.share.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtils {

    const val MENU_CAMERA_INTENT = 0
    const val MENU_CAMERA_X = 1

    fun showAlertDialog(
        context: Context,
        @StringRes message: Int,
        @StringRes positiveButton: Int = R.string.ok
    ) {
        val builder = MaterialAlertDialogBuilder(context)
        builder.apply {
            setMessage(message)
            setPositiveButton(positiveButton) { dialog, _ -> dialog.dismiss() }
        }.create()
        builder.show()
    }

    fun showButtonAlertDialog(
        context: Context,
        @StringRes message: Int,
        @StringRes positiveButton: Int = R.string.ok,
        @StringRes negativeButton: Int = R.string.cancel,
        positiveButtonListener: OnClickListener
    ) {
        val builder = MaterialAlertDialogBuilder(context)
        builder.apply {
            setMessage(message)
            setPositiveButton(positiveButton, positiveButtonListener)
            setNegativeButton(negativeButton) { dialog, _ -> dialog.dismiss() }
        }.create()
        builder.show()
    }

    fun showListAlertDialog(
        context: Context,
        @ArrayRes items: Int,
        onClickListener: OnClickListener
    ) {
        val builder = MaterialAlertDialogBuilder(context)
        builder.apply {
            setItems(items, onClickListener)
        }.create()
        builder.show()
    }
}