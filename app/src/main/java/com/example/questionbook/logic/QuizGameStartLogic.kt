package com.example.questionbook.logic

import com.example.questionbook.QuestionItem
import com.example.questionbook.room.QuestionQuizEntity

/**
 * ■実装するメソッド
 * ・getIndex():Int   現在のクイズのインデックス番号を取得する。
 * ・check():Boolean  選択した解答案の正誤を確認するメソッド。
 * ・print()          現在のクイズのデータを表示させるためのメソッド
 *
 * ※現在のクイズを操作するためのインターフェイス、メソッドの追加又は、機能変更有
 * 2021/04/19 11:58
 */
interface QuizGameStartLogic {
    fun getIndex():Int
    fun check(select:String):Boolean
    fun getRandom():List<QuestionItem>
}

/**
 * クイズを行うための機能を制御するためのロジックとなるクラスを作成。
 */
class ConcreteQuizGameStartLogic(
        private val data:List<QuestionQuizEntity>,
        private val title:String
):QuizGameStartLogic{

    /**
     * view model から取得したデータを List<QuestionItem> に変換をする
     */
    private val _questionItemList:List<QuestionItem> by lazy {
        data.map {
            QuestionItem(
                questionStatement   = it.quizStatement,
                questionFirs        = it.quizFirs,
                questionSecond      = it.quizSecond,
                questionThird       = it.quizThird,
                questionRight       = it.quizRight,
                questionTitle       = title,
                selectAnswers       =
                mutableListOf<String>().apply {
                    add(it.quizRight)
                    add(it.quizFirs)
                    add(it.quizSecond)
                    add(it.quizThird)
                    shuffled()
                }
            )
        }.toList()
    }

    val questionItemList:List<QuestionItem>
        get() = _questionItemList

    override fun getIndex(): Int = data.size

    override fun getRandom() = _questionItemList.shuffled()

    override fun check(select: String): Boolean {
       return true
    }

    fun get() = data

    companion object{
        var singleton:ConcreteQuizGameStartLogic? = null
        fun getInstance(
                data:List<QuestionQuizEntity>,
                title:String
        ) =  singleton ?: ConcreteQuizGameStartLogic(data, title)
    }
}