package com.example.questionbook.dialog

import android.app.AlertDialog
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import com.example.questionbook.R

class WorkBookDialog(
        private val activity: FragmentActivity,
        @LayoutRes private val resource:Int
    ):DialogProduct(activity) {

    private val dialogView:View by lazy {
        activity.layoutInflater.inflate(resource,null)
    }

    override fun create(): AlertDialog =
            super.dialog.also {
                it.setTitle(activity.getString(R.string.dialog_workbook_title))
                it.setView(dialogView as LinearLayout)
            }.create()

    override fun getView(): View {
        return dialogView
    }

    companion object{
        var singleton:WorkBookDialog? =null
        fun getInstance(activity: FragmentActivity,@LayoutRes resource:Int):WorkBookDialog =
            singleton?:WorkBookDialog(activity,resource)
    }

}