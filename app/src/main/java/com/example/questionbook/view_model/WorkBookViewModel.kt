package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.*
import com.example.questionbook.room.QuestionDatabase
import com.example.questionbook.room.QuestionWorkBookDao
import com.example.questionbook.room.QuestionWorkBookEntity
import com.example.questionbook.room.WorkBookWithProblemsAndAccuracy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkBookViewModel(app:Application):AndroidViewModel(app) {
    private val  dao =
        QuestionDatabase
        .getInstance(app,viewModelScope)
        .getWorkBookDao()

    val data:LiveData<List<WorkBookWithProblemsAndAccuracy>> by lazy {
        dao.getAll()
    }

    fun insert(entity:QuestionWorkBookEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(entity = entity)
        }

    fun update(entity: QuestionWorkBookEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(entity = entity)
        }

    fun delete(entity: QuestionWorkBookEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(entity = entity)
        }
}