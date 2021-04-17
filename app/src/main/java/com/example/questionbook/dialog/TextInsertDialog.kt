package com.example.questionbook.dialog

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import com.example.questionbook.R

class TextInsertDialog(
        private  val activity: FragmentActivity,
        @LayoutRes private val resource:Int
        ):DialogProduct(activity = activity) {

    private val view = activity.layoutInflater.inflate(R.layout.dialog_form_filst_text_layout,null)

    override fun create(): AlertDialog =
            super.dialog.setTitle(activity.getString(R.string.dialog_text_title))
                    .setView(view as LinearLayout)
                    .setNegativeButton(activity.getString(R.string.dialog_negative_button) ) {
                        dialog, which ->
                        dialog.cancel()
                    }
                    .create()

    override fun getView(): View = view

    override fun  getValue():String {
        TODO("Not yet implemented")
    }

    companion object{
        private var singleton:TextInsertDialog? = null
        fun getInstance(
                activity: FragmentActivity,
                @LayoutRes resource:Int
        ):TextInsertDialog = singleton?: TextInsertDialog(activity, resource)
    }
}
