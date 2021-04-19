package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.*
import com.example.questionbook.room.QuestionAnswerEntity
import com.example.questionbook.room.QuestionDatabase
import com.example.questionbook.room.QuestionQuizEntity
import com.example.questionbook.room.TextWithAnswer
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

    private val answerDao = db.getAnswerDao()

    private val quizDao = db.getQuizDao()

    private var _data = quizDao.getAll()

    val data:LiveData<List<QuestionQuizEntity>>
        get() = _data

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