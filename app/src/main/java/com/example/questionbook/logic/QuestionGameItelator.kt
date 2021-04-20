package com.example.questionbook.logic

import com.example.questionbook.QuestionItem
import com.example.questionbook.room.QuestionQuizEntity
import kotlin.math.log

/**
 * クイズのデータをまとめるためのインターフェイスいわば集合体の役割を果たす。
 */
interface AggregationQuestionItem {
    fun getSize():Int
    fun getItemAt(index:Int):QuestionItem
    fun createIterator():QuestionItemIterator
}


/**
 * クイズでの正解を表示させるためのインターフェイス。
 */
interface QuizGameLogic{
    fun isCorrectAnswer(item:QuestionItem, answer:String):Boolean
    fun isIncorrectAnswer(item:QuestionItem, answer:String):Boolean
}

/**
 * クイズのデータを数えるためのインターフェイス。
 */
interface QuestionItemIterator{
    fun next():QuestionItem
    fun hasNext():Boolean
    fun getIndex():Int
    fun getSize():Int
}

/**
 * 問題となるクイズのデータを保持するクラス。
 */
class QuestionItemShelf(
        private val data:List<QuestionQuizEntity>,
        private val title:String
    ):AggregationQuestionItem{

    private val logic:QuizGameLogic by lazy {
        object :QuizGameLogic{
            override fun isCorrectAnswer(item:QuestionItem, answer:String):Boolean =
                    item.entity.quizRight == answer

            override fun isIncorrectAnswer(item:QuestionItem, answer:String):Boolean =
                    item.entity.quizRight == answer
        }
    }

    private var correctAnswerCount      =   0
    private var incorrectAnswerCount    =   0

    private val _questionItemList:List<QuestionItem> by lazy {
        data.map {
            QuestionItem(
                    questionTitle = title,
                    answerCheck = 0,
                    entity = it,
                    selectAnswers =
                    mutableListOf<String>().apply {
                        add(it.quizRight)
                        add(it.quizFirs)
                        add(it.quizSecond)
                        add(it.quizThird)
                        shuffled()
                    })
        }.toList().shuffled()
    }

    /**
     * ■解答が正解の場合は true
     * @param item   クイズを保存するためのオブジェクト
     * @param answer 選択した解答案
     */
    fun correctAnswerCount(item:QuestionItem, answer:String){
        if (logic.isCorrectAnswer(item, answer)) {
            correctAnswerCount++
            item.answerCheck = 1
        }
    }

    /**
     * ■解答が不正解の場合は true
     * @param item   クイズを保存するためのオブジェクト
     * @param answer 選択した解答案
     */
    fun incorrectAnswerCount(item:QuestionItem, answer:String){
        if (logic.isIncorrectAnswer(item, answer)) {
            incorrectAnswerCount++
            item.answerCheck = 2
        }
    }

    override fun getSize(): Int =_questionItemList.size

    /**
     * ■単一のQuestionItemオブジェクトを取得
     * @param index リストの添え字番号となる物
     * @return リストの番号に基づいたアイテム
     */
    override fun getItemAt(index:Int):QuestionItem {
        return _questionItemList[index]
    }

    /**
     * ■イテレーターの生成を行う。
     */
    override fun createIterator(): QuestionItemIterator {
        return ConnCreteQuestionItemIterator(this)
    }
}

/**
 * クイズとなる問題テキストの数を管理するクラス
 */
class ConnCreteQuestionItemIterator(
        private val questionShelf:QuestionItemShelf
    ):QuestionItemIterator{

    private var index = 0

    override fun next(): QuestionItem {
        return questionShelf.getItemAt(index).apply { index++ }
    }

    override fun hasNext(): Boolean = index < questionShelf.getSize()

    override fun getIndex(): Int = index

    override fun getSize(): Int = questionShelf.getSize()

}
