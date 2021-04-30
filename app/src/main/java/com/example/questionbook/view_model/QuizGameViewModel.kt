package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.*
import com.example.questionbook.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ■保持されているデータ
 * ・Problem 問題集
 * ・Answer 解答
 * @param app  　アプリケーションの指定
 */
class QuizGameViewModel(private val app:Application): AndroidViewModel(app) {

    private val db   = QuestionDatabase.getInstance(app,viewModelScope)

    private val accuracyDao:QuestionAccuracyDao
    private val quizDao:QuestionLeafDao
    private val historyDao:QuestionHistoryDao

    init {
        db.run {
            accuracyDao = getAccuracyDao()
            quizDao     = getQuizDao()
            historyDao  = getHistoryDao()
        }
    }

    private var _quizEntityList = quizDao.getList()

    val quizEntityList:LiveData<List<QuestionLeafEntity>>
        get() = _quizEntityList


    fun accuracyInsert(entity:QuestionAccuracyEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            accuracyDao.insert(entity = entity)
        }

    fun accuracyUpdate(entity: QuestionAccuracyEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            accuracyDao.update(entity = entity)
        }

    fun accuracyDelete(entity:QuestionAccuracyEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            accuracyDao.delete(entity = entity)
        }

    fun historyInsert(entity: QuestionHistoryEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                historyDao.insert( entity = entity )
            }

    fun  historyUpdate(entity: QuestionHistoryEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                historyDao.update( entity = entity)
            }

    fun historyDelete(entity: QuestionHistoryEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                historyDao.delete(entity = entity)
            }
}