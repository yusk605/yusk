package com.example.questionbook

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner

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

