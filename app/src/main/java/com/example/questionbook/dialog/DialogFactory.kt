package com.example.questionbook.dialog

import androidx.fragment.app.DialogFragment

interface DialogFactoryImpl<T:DialogFragment>{
    fun create(dialogClass:Class<T>):DialogFragment
}




class CategoryDialogFactory:DialogFactoryImpl<CategoryDialog>{
    override fun create(dialogClass: Class<CategoryDialog>):DialogFragment {
        if (dialogClass.isAssignableFrom(CategoryDialog::class.java))
            return dialogClass as CategoryDialog

        throw IllegalArgumentException("Type mismatch CategoryDialog")
    }
}