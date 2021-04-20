package com.example.questionbook.logic

import com.example.questionbook.QuestionItem
import com.example.questionbook.room.QuestionQuizEntity
import kotlin.math.log

interface AggregationQuestionItem {
    fun check(select:String):Boolean
    fun getSize():Int
    fun getItemAt(index:Int):QuestionItem
}

interface QuestionItemIterator{
    fun next():QuestionItem
    fun hasNext():Boolean
}


class QuestionItemShelf(
        private val data:List<QuestionQuizEntity>,
        private val title:String
    ):AggregationQuestionItem{

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
        }.toList().shuffled()
    }

    override fun check(select: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getSize(): Int {
        return _questionItemList.size
    }

    override fun getItemAt(index:Int): QuestionItem {
        return _questionItemList[index]
    }
}


class ConnCreteQuestionItemIterator(
        private val questionShelf:QuestionItemShelf
    ):QuestionItemIterator{

    private var index:Int=0

    override fun next(): QuestionItem {
        return questionShelf.getItemAt(index).apply { index++ }
    }

    override fun hasNext(): Boolean {
        return index < questionShelf.getSize()
    }

}
