package com.example.questionbook

import android.os.Parcelable
import com.example.questionbook.room.QuestionQuizEntity
import kotlinx.android.parcel.Parcelize

/**
 * ■問題を表示させるためのオブジェクト
 * @param entity        クイズを格納するためのエンティティ。
 * @param answerCheck   0..デフォルトの値 1..正解となる値 2..不正解となる値
 * @param selectAnswers クイズゲームを行う際に選択案をシャッフルさせるためのリスト。
 */
data class QuestionItem(
        var questionTitle:String,
        var answerCheck:Int,
        val entity: QuestionQuizEntity,
        val selectAnswers:MutableList<String>
        )
/*
    @Note　
    問題に必要な値を取得するものを考える。
    クエッションアイテム内の属性に question_quiz のエンティティをプロパティに渡すこと。

 */

/**
 * ■リザルト画面に結果となるデータを表示させるためのオブジェクト
 * @param questionItemList クイズを行ったデータを保持するクラス
 */
@Parcelize
data class ResultItem(
        val questionItemList:MutableList<QuestionItem>
):Parcelable

