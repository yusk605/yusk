package com.example.questionbook

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.FragmentActivity
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
            0 -> getString(R.string.menu_side_holder)
            1 -> getString(R.string.menu_side_game)
            else -> getString(R.string.menu_side_accuracy)
        }

