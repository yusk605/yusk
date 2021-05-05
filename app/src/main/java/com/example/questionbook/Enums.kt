package com.example.questionbook

/**
 * ■ ポップアップメニューを表示した時に選択した項目に値を持たせるためのクラス
 * @param item 選択した項目をInt型の数値で表す。
 */
enum class PopupMenuSelectItems(val item:Int){
    SELECT_FIRST(1),
    SELECT_SECOND(2),
    SELECT_THIRD(3),
    NOT_SHOW(-1)
 }

