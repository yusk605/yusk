package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.questionbook.room.*

/**
 * クイズゲームを行った際に必要なデータを取得する。
 */
class ResultViewModel(application: Application):AndroidViewModel(application) {
    private val db = QuestionDatabase.getInstance(application,viewModelScope)

    private val historyDao:QuestionHistoryDao
    private val quizDao:QuestionLeafDao
    private val accuracyDao:QuestionAccuracyDao

    init {
        db.run {
            historyDao  = getHistoryDao()
            quizDao     = getQuizDao()
            accuracyDao = getAccuracyDao()
        }
    }

    private var _quizData:LiveData<List<QuestionLeafEntity>>
        = quizDao.getList()

    private var _accuracyWithHistory:LiveData<List<AccuracyWithHistory>>
        = historyDao.getWithAccuracy()

    private var _quizWithHistory:LiveData<List<LeafWithHistory>>
        = historyDao.getWithLeaf()

    val quizData
        get() = _quizData

    val accuracyWithHistory
        get() = _accuracyWithHistory

    val quizWithHistory
        get() = _quizWithHistory

}
