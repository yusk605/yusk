package com.example.questionbook.dialog

import android.app.AlertDialog
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity

/**
 * 抽象的なファクトリーとなるインターフェイス。
 */
interface DialogFactoryImpl<T:DialogProduct>{
    fun create(dialogClass:Class<T>):DialogProduct
}

/**
 * 抽象的なダイヤログクラス
 * @param activity  フラグメント内で取得することが出来るコンテキス
 */
abstract class DialogProduct(
        private val activity: FragmentActivity
    ){

    private val _dialog: AlertDialog.Builder by lazy { AlertDialog.Builder(activity) }

    protected val dialog:AlertDialog.Builder
        get() = _dialog

    abstract fun create():AlertDialog
    abstract fun getView():View
    abstract fun getValue():String
    
}


/**
 * ■カテゴリー一覧からダイヤログを表示させる用のダイヤログクラスを作成。
 * @param activity フラグメントのコンテキスト。
 * @param resource 表示させたダイヤログのレイアウトを表示。
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

/**
 * ■問題集一覧からダイヤログを表示させる用のダイヤログクラスを作成。
 * @param activity フラグメントのコンテキスト。
 * @param resource 表示させたダイヤログのレイアウトを表示。
 */
class WorkBookDialogFactory(
        private val activity: FragmentActivity,
        @LayoutRes private val resource:Int
        ):DialogFactoryImpl<WorkBookDialog>{

    override fun create(dialogClass: Class<WorkBookDialog>): DialogProduct {
        if(dialogClass.isAssignableFrom(WorkBookDialog::class.java))
            return WorkBookDialog.getInstance(activity, resource)

        throw IllegalArgumentException("Type mismatch WorkBookDialog")
    }
}

/**
 * ■問題となるテキスト一覧から表示させる
 * @param activity フラグメントのコンテキスト。
 * @param resource 表示させたダイヤログのレイアウトを表示。
 */
class PageQuizDialogFactory(
        private val activity: FragmentActivity,
        @LayoutRes private val resource:Int
        ):DialogFactoryImpl<PageQuizDialog>{
    override fun create(dialogClass: Class<PageQuizDialog>): DialogProduct {
        if(dialogClass.isAssignableFrom(PageQuizDialog::class.java))
            return PageQuizDialog.getInstance(activity, resource)

        throw IllegalArgumentException("Type mismatch TextDialog")
    }
}

/**
 *
 */
class TextInsertDialogFactory(
        private val activity: FragmentActivity,
        @LayoutRes private val resource:Int
):DialogFactoryImpl<TextInsertDialog>{
    override fun create(dialogClass: Class<TextInsertDialog>): DialogProduct {
        if(dialogClass.isAssignableFrom(TextInsertDialog::class.java))
            return TextInsertDialog.getInstance(activity, resource)

        throw IllegalArgumentException("Type mismatch TextDialog")
    }
}

class AnswerDialogFactory(
            private val activity: FragmentActivity,
            @LayoutRes private val resource:Int
        ):DialogFactoryImpl<AnswerInsertDialog>{
    override fun create(dialogClass: Class<AnswerInsertDialog>): DialogProduct {
        if (dialogClass.isAssignableFrom(AnswerInsertDialog::class.java))
            return AnswerInsertDialog.getInstance(activity, resource)

        throw IllegalArgumentException("Type mismatch AnswerDialog")
    }
}
