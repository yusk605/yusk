package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.questionbook.room.QuestionDatabase
import com.example.questionbook.room.QuestionQuizEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizListViewModel(application: Application):AndroidViewModel(application) {

    private val quizDao = QuestionDatabase.getInstance(application,viewModelScope).getQuizDao()

    private var _quizEntityList = quizDao.getList()

    val quizEntityList:LiveData<List<QuestionQuizEntity>>
        get() = _quizEntityList


    fun quizInsert(entity: QuestionQuizEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                quizDao.insert( entity = entity )
            }

    fun  quizUpdate(entity: QuestionQuizEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                quizDao.update( entity = entity)
            }
    fun quizDelete(entity: QuestionQuizEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                quizDao.delete(entity = entity)
            }

}