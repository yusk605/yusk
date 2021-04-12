package com.example.questionbook.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

/**
 * 抽象的なファクトリーとなるインターフェイス。
 */
interface DialogFactoryImpl<T:DialogProduct>{
    fun create(dialogClass:Class<T>):DialogProduct
}

/**
 * 抽象的なダイヤログクラス
 * @param activity  フラグメント内で取得することが出来るコンテキスト
 * @param resource  表示させたいレイアウトファイル 例：R.layout.....xml
 */
abstract class DialogProduct(
        private val activity: FragmentActivity
    ){

    private val _dialog: AlertDialog.Builder by lazy { AlertDialog.Builder(activity) }

    protected val dialog: AlertDialog.Builder
        get() = _dialog

    abstract fun create():AlertDialog
    abstract fun getView():View
}


/**
 *
 */
class CategoryDialogFactory (
        private val activity: FragmentActivity,
        @LayoutRes private val resource:Int
):DialogFactoryImpl<CategoryDialog>{
    override fun create(dialogClass: Class<CategoryDialog>):DialogProduct {
        if (dialogClass.isAssignableFrom(CategoryDialog::class.java))
            return CategoryDialog.getInstance(activity,resource)

        throw IllegalArgumentException("Type mismatch CategoryDialog")
    }
}