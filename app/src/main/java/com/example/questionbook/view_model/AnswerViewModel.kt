package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.*
import com.example.questionbook.room.QuestionAnswerDao
import com.example.questionbook.room.QuestionAnswerEntity
import com.example.questionbook.room.QuestionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnswerViewModel(private val app:Application): AndroidViewModel(app) {
    private val db   = QuestionDatabase.getInstance(app,viewModelScope as LifecycleCoroutineScope)
    private val dao  = db.getAnswerDao()
    private var _data = dao.getAll()

    val data:LiveData<List<QuestionAnswerEntity>>
        get() = _data

    fun insert(entity:QuestionAnswerEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(entity = entity)
        }

    fun update(entity: QuestionAnswerEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(entity = entity)
        }

    fun delete(entity: QuestionAnswerEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(entity = entity)
        }
}