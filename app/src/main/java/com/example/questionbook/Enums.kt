package com.example.questionbook

/**
 * 削除確定タイトル画面から遷移を行う際に渡す値。
 * @param get 値の取得　CATEGORY..1 WORKBOOK..2 LEAF..3
 */
enum class GarbageCanActionButton(val get:Int){
    CATEGORY(1),
    WORKBOOK(2),
    LEAF(3),
 }

