package com.example.questionbook.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.questionbook.R

class CategoryDialog private constructor(
        private val activity: FragmentActivity,
        @LayoutRes private val resource:Int
    ):DialogProduct(activity) {

    private val dialogView:View by lazy { activity.layoutInflater.inflate(resource,null) }

    override fun create(): AlertDialog =
            super.dialog.also {
                it.setTitle(activity.getString(R.string.dialog_category_title))
                it.setView(dialogView as LinearLayout)
            }.create()

    override fun getView(): View = dialogView

    companion object{
            private var singleton:CategoryDialog? = null
            fun getInstance(
                    activity: FragmentActivity,
                    @LayoutRes resource:Int
            ):CategoryDialog = singleton?:CategoryDialog(activity, resource)
        }
    }