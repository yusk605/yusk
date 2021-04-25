package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.questionbook.room.QuestionDatabase
import com.example.questionbook.room.QuestionQuizEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizViewModel(application: Application):AndroidViewModel(application) {
    private val dao = QuestionDatabase.getInstance(application,viewModelScope).getQuizDao()

    private var _data = dao.getAll()

    val data:LiveData<List<QuestionQuizEntity>>
        get() = _data

    fun insert(entity: QuestionQuizEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                dao.insert( entity = entity )
            }
    fun  update(entity: QuestionQuizEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                dao.update( entity = entity)
            }
    fun delete(entity: QuestionQuizEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                dao.delete(entity = entity)
            }
}