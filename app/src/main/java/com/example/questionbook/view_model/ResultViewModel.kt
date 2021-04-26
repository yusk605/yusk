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

    private var _quizData:LiveData<List<QuestionQuizEntity>> =
            quizDao.getList()
/*
    private var _accuracyWithHistory:LiveData<List<AccuracyWithHistory>> =
            historyDao.getWithAccuracy()

    private var _quizWithHistory:LiveData<List<QuizWithHistory>> =
            historyDao.getWithQuiz()
*/
    val quizData
        get() = _quizData
/*
    val accuracyWithHistory
        get() = _accuracyWithHistory

    val quizWithHistory
        get() = _quizWithHistory
*/
}
