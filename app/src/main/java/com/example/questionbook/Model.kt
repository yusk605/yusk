package com.example.questionbook

import android.os.Parcelable
import com.example.questionbook.room.QuestionLeafEntity
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * ■問題を表示させるためのオブジェクト
 * @param entity        クイズを格納するためのエンティティ。
 * @param answerCheck   0..デフォルトの値 1..正解となる値 2..不正解となる値
 * @param selectAnswers クイズゲームを行う際に選択案をシャッフルさせるためのリスト。
 */
data class QuestionItem(
        var questionTitle:String,
        var answerCheck:Int,
        var selectAnswer:String="",
        var historyQuizNumber:Int=0,
        val entity: QuestionLeafEntity
        )
/*
    @Note　
    問題に必要な値を取得するものを考える。
    クエッションアイテム内の属性に question_quiz のエンティティをプロパティに渡すこと。
 */


/**
 * ■クイズゲームを行った際にその結果を保存する
 */
@Parcelize
data class QuizResult(
        val resultTitle: String,
        val resultText:String,
        val resultProgress:Int,
        val resultAccuracy:Float,
        val relationWorkBookNo:Int,
        val relationAccuracyNo:Int
):Parcelable



