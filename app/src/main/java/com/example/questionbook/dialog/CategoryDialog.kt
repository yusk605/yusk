package com.example.questionbook.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.questionbook.R

class CategoryDialog:DialogFragment() {

    private val dialog:AlertDialog.Builder = AlertDialog.Builder(activity)
    private val layout = activity?.layoutInflater?.inflate(
        R.layout.dialog_category_layout,null ) as LinearLayout

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return dialog.also {
            it.setTitle(getString(R.string.dialog_category_title))
            it.setIcon(R.drawable.question_book_holder)
            it.setView(layout)
        }.create()
    }

    //TODO カスタムダイアログレイアウトでの値を取得するウィジェットの処理を記述すること。

}