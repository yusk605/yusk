package com.example.questionbook.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

interface DialogFactoryImpl<T:DialogProduct>{
    fun create(dialogClass:Class<T>):DialogProduct
}

abstract class DialogProduct(
        private val activity: FragmentActivity,
        @LayoutRes private val resource:Int
    ){

    private val _dialog: AlertDialog.Builder by lazy { AlertDialog.Builder(activity) }
    private val _view:View by lazy { activity.layoutInflater.inflate(resource,null) }

    val view:View
        get() = _view

    val dialog: AlertDialog.Builder
        get() = _dialog

    abstract fun create():AlertDialog
}



class CategoryDialogFactory(
        private val activity: FragmentActivity,
        @LayoutRes private val resource:Int
):DialogFactoryImpl<CategoryDialog>{
    override fun create(dialogClass: Class<CategoryDialog>):DialogProduct {
        if (dialogClass.isAssignableFrom(CategoryDialog::class.java))
            return CategoryDialog(activity,resource)

        throw IllegalArgumentException("Type mismatch CategoryDialog")
    }
}