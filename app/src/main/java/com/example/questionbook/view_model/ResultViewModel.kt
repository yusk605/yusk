package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.questionbook.room.*

class ResultViewModel(application: Application):AndroidViewModel(application) {
    private val db = QuestionDatabase.getInstance(application,viewModelScope)

    private val historyDao:QuestionHistoryDao
    private val quizDao:QuestionQuizDao
    private val accuracyDao:QuestionAccuracyDao

    init {
        db.run {
            historyDao  = getHistoryDao()
            quizDao     = getQuizDao()
            accuracyDao = getAccuracyDao()
        }
    }

    private var _quizData:LiveData<List<QuestionQuizEntity>> = quizDao.getList()

    val quizData
        get() = _quizData

}