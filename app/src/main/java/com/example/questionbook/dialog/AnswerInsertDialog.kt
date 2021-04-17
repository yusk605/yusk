package com.example.questionbook.dialog

import android.app.Activity
import android.app.AlertDialog
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import com.example.questionbook.R

class AnswerInsertDialog(
        private val activity: FragmentActivity,
        @LayoutRes private val resource:Int
        ):DialogProduct(activity = activity) {

    private val view = activity.layoutInflater.inflate(resource,null)

    override fun create(): AlertDialog {
        return super.dialog
                .setTitle(activity.getString(R.string.dialog_text_title))
                .setNegativeButton(activity.getString(R.string.dialog_negative_button)){
                    dialog, which ->
                    dialog.cancel()
                    }
                .create()
    }

    override fun getView(): View {
        return  view
    }

    override fun getValue(): String {
        TODO("Not yet implemented")
    }

    companion object{
        private var  singleton:AnswerInsertDialog? =null
        fun getInstance(
                activity: FragmentActivity,
                resource:Int
        ) = singleton?:AnswerInsertDialog(activity, resource)
    }
}