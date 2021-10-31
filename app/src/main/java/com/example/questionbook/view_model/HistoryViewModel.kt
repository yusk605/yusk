package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questionbook.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    private var _historyList  = historyDao.getList()
    private var _dataWithAccuracy = historyDao.getWithAccuracy()
    private var _dataWithLeaf = historyDao.getWithLeaf()

    val historyList
        get() = _historyList

    val accuracyList
        get() = _accuracyList

    val dataWithAccuracy
        get() = _dataWithAccuracy

    val dataWithLeaf
        get() = _dataWithLeaf

    fun updateHistory(entity:QuestionHistoryEntity){
        viewModelScope.launch {
            historyDao.update(entity)
        }
    }

    fun getAccuracyLast():List<QuestionAccuracyEntity>{
        return  accuracyDao.getLast()
    }

    fun accuracyDelete(entity:QuestionAccuracyEntity) =
            viewModelScope.launch(Dispatchers.IO){
                accuracyDao.delete(entity)
        }
    fun accuracyUpdate(entity: QuestionAccuracyEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                accuracyDao.update(entity)
            }
}