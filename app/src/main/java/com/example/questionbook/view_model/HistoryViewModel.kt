package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questionbook.room.QuestionAccuracyDao
import com.example.questionbook.room.QuestionDatabase
import com.example.questionbook.room.QuestionHistoryDao

class HistoryViewModel(private val app:Application):AndroidViewModel(app) {
    private val db = QuestionDatabase.getInstance(app,viewModelScope)
    private val historyDao:QuestionHistoryDao
    private val accuracyDao:QuestionAccuracyDao
    init {
        db.run {
            historyDao = getHistoryDao()
            accuracyDao = getAccuracyDao()
        }
    }
    private var _accuracyList = accuracyDao.getList()
    private var _dataWithAccuracy = historyDao.getWithAccuracy()
    private var _dataWithLeaf = historyDao.getWithLeaf()

    val accuracyList
        get() = _accuracyList

    val dataWithAccuracy
        get() = _dataWithAccuracy

    val dataWithLeaf
        get() = _dataWithLeaf

}