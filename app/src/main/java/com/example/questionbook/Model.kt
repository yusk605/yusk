package com.example.questionbook

/**
 * ■問題を表示させるためのオブジェクト
 * @param questionTitle 問題となるタイトル
 * @param questionStatement 問題文
 * @param questionFirs   解答案1
 * @param questionSecond 解答案2
 * @param questionThird　解答案3
 * @param questionRight  正解
 * @param selectAnswers クイズゲームを行う際に選択案をシャッフルさせるためのリスト。
 */
data class QuestionItem(
        val questionStatement:String,
        val questionTitle:String,
        val questionFirs:String,
        val questionSecond:String,
        val questionThird:String,
        val questionRight:String,
        val selectAnswers:MutableList<String>
        )
/*
    @Note　問題に必要な値を取得するものを考える。
 */