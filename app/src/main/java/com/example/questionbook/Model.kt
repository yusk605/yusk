package com.example.questionbook

import com.example.questionbook.room.QuestionQuizEntity

/**
 * ■問題を表示させるためのオブジェクト
 * @param questionTitle 問題となるタイトル
 * @param questionStatement 問題文
 * @param questionFirs   解答案1
 * @param questionSecond 解答案2
 * @param questionThird　解答案3
 * @param questionRight  正解
 * @param answerCheck   0..デフォルトの値 1..正解となる値 2..不正解となる値
 * @param selectAnswers クイズゲームを行う際に選択案をシャッフルさせるためのリスト。
 */
data class QuestionItem(
        val questionStatement:String,
        val questionTitle:String,
        val questionFirs:String,
        val questionSecond:String,
        val questionThird:String,
        val questionRight:String,
        var answerCheck:Int,
        val entity: QuestionQuizEntity,
        val selectAnswers:MutableList<String>
        )
/*
    @Note　
    問題に必要な値を取得するものを考える。
    クエッションアイテム内の属性に question_quiz のエンティティをプロパティに渡すこと。

 */