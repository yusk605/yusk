package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.*
import com.example.questionbook.room.*
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
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

    private var _accuracyEntityList = accuracyDao.getList()

    val accuracyEntityList
        get() = _accuracyEntityList

    val quizEntityList:LiveData<List<QuestionLeafEntity>>
        get() = _quizEntityList

    fun accuracyInsertRx(entity:QuestionAccuracyEntity) = accuracyDao.insertRx(entity)

    fun getAccuracyLast()=accuracyDao.getLast()

    fun accuracyInsert(entity:QuestionAccuracyEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            accuracyDao.insert(entity = entity)
        }


    fun historyInsert(entity: QuestionHistoryEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                historyDao.insert( entity = entity )
            }
}