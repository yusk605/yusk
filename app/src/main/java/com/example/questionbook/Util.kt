package com.example.questionbook

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar

/**
 * ■スピナーでのドロップリストに保存したい値を入れる
 * @param data ドロップリストに表示させたい文字列
 * @param context コンテキストルート
 */
fun Spinner.setAdapter(data:List<String>,context: Context){
    adapter = ArrayAdapter(
        context,
        android.R.layout.simple_spinner_dropdown_item,
        data
    )
}

/**
 * ■スナックバーを表示させるためのメソッド
 * @param msg スナックバーの文字列を表示させるため
 */
fun View.showSnackBar(msg:String){
    Snackbar.make(this,msg, Snackbar.LENGTH_SHORT).show()
}

fun FragmentActivity.getMag(no:Int):String =
        when (no){
            0 -> getString(R.string.snackbar_holder_msg)
            1 -> getString(R.string.snackbar_game_msg)
            else -> getString(R.string.snackbar_statistics_msg)
        }

/**
 * ■バンドルを新規で生成したのち、値を格納する。
 * @param key   値を保存領域に格納しその格納した値を取り出すためのキーを指定
 * @param value 保存領域に格納する値
 */
fun newBundleToPutInt(key:String, value:Int)=
        Bundle().apply {
            putInt(key,value)
        }

/**
 * ■サイドメニューから渡される値を基に遷移を行う。
 * @param view      必要となるビュー
 * @param bundle    必要な値を格納するためのオブジェクト
 */
fun Int.actionWorkBook(view: View, bundle: Bundle){
    when(this){
        0 -> Navigation.findNavController(view)
                .navigate(R.id.action_workBookListFragment_to_problemListFragment,bundle)
        1 -> Navigation.findNavController(view)
                .navigate(R.id.action_workBookListFragment_to_gameStartFragment,bundle)
        2 -> return
    }
}

fun Int.isHolder():Boolean = this==MainActivity.actionHolderValue
