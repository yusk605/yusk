package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.*
import com.example.questionbook.room.ProblemWithAnswerAndHistory
import com.example.questionbook.room.QuestionAnswerEntity
import com.example.questionbook.room.QuestionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ■保持されているデータ
 * ・Problem 問題集
 * ・Answer 解答
 * @param app  　アプリケーションの指定
 */
class QuestionFormViewModel(private val app:Application): AndroidViewModel(app) {

    private val db   = QuestionDatabase.getInstance(app,viewModelScope as LifecycleCoroutineScope)

    private val problemDao = db.getProblemDao()

    private val answerDao = db.getAnswerDao()

    private var _problemData = problemDao.getAll()

    val problemData:LiveData<List<ProblemWithAnswerAndHistory>>
        get() = _problemData

    fun insert(entity:QuestionAnswerEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            answerDao.insert(entity = entity)
        }

    fun update(entity: QuestionAnswerEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            answerDao.update(entity = entity)
        }

    fun delete(entity: QuestionAnswerEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            answerDao.delete(entity = entity)
        }
}