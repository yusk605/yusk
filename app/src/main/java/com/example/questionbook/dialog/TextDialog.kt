package com.example.questionbook.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import com.example.questionbook.R

class TextDialog private constructor(
        private val activity: FragmentActivity,
        @LayoutRes private val resource:Int
        ):DialogProduct(activity) {

    private val dialogView:View by lazy {
        activity.layoutInflater.inflate(resource,null)
    }

    override fun create(): AlertDialog {
       return  super.dialog
                .setTitle(activity.getString(R.string.dialog_text_title))
                .setView(dialogView as LinearLayout)
                .create()
    }

    override fun getView(): View = dialogView

    override fun  getValue():String {
        TODO("Not yet implemented")
    }

    companion object{
        var instance:TextDialog? = null
        fun getInstance(
                activity:FragmentActivity,
                @LayoutRes resource:Int
        ):TextDialog = instance?:TextDialog(activity, resource)
    }
}