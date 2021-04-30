package com.example.questionbook.logic

import com.example.questionbook.QuestionItem
import com.example.questionbook.room.QuestionLeafEntity

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
 * ■問題となるクイズのデータを保持するクラス。
 * プライマリーコンストラクターに二つの引き数を持ち、引数としては
 * data:List<QuestionQuizEntity>
 * title:String
 *
 */
class QuestionItemShelf(
        private val data:List<QuestionLeafEntity>,
        private val title:String
    ):AggregationQuestionItem{

    private val logic:QuizGameLogic by lazy {
        object :QuizGameLogic{
            override fun isCorrectAnswer(item:QuestionItem, answer:String):Boolean =
                    item.entity.leafRight == answer

            override fun isIncorrectAnswer(item:QuestionItem, answer:String):Boolean =
                    item.entity.leafRight != answer
        }
    }

    private var correctAnswerCount      =   0
    private var incorrectAnswerCount    =   0

    /**
     * クイズの表示又は、入力を行うための必要なデータの集合体。
     * 初期処理としては、question_quiz のエンティティを
     * workBookNoでデータリストに集約を行った後、リストシャッフルを行い、
     * 出題される番号を保存するために、シャッフル後に連番を振る。
     */
    private val _questionItemList:List<QuestionItem> by lazy {
        data.map {
            QuestionItem(
                    questionTitle = title,
                    answerCheck = 0,
                    entity = it,
                    selectAnswers =
                    mutableListOf<String>().apply {
                        add(it.leafRight)
                        add(it.leafFirs)
                        add(it.leafSecond)
                        add(it.leafThird)
                    })
        }.toList().shuffled().also {
            list->
            list.map {
                it.historyQuizNumber = list.indexOf(it)+1 //インデックス番号は0番スタートのため
            }
        }
    }

    val questionItemList
        get() = _questionItemList

    /**
     * ■正解の場合は true を返したのち、answerCheck = 1
     * @param item   クイズを保存するためのオブジェクト（QuestionItem）
     * @param answer 選択した解答案
     */
    fun correctAnswerCount(item:QuestionItem, answer:String){
        if (logic.isCorrectAnswer(item, answer)) {
            correctAnswerCount++
            item.answerCheck = 1
        }
    }

    /**
     * ■不正解の場合は true を返したのち、answerCheck = 2
     * @param item   クイズを保存するためのオブジェクト（QuestionItem）
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
        val item = questionShelf.getItemAt(index)
        index++
        return item.also { it.selectAnswers.shuffle() }
    }

    override fun hasNext(): Boolean = index < questionShelf.getSize()
    override fun getIndex(): Int = index
    override fun getSize(): Int = questionShelf.getSize()
}
