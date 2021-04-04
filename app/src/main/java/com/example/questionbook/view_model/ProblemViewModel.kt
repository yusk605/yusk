package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.*
import com.example.questionbook.room.ProblemWithAnswerAndHistory
import com.example.questionbook.room.QuestionDatabase
import com.example.questionbook.room.QuestionProblemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProblemViewModel(private val app:Application): AndroidViewModel(app) {

    private val dao = QuestionDatabase
        .getInstance(app,viewModelScope as LifecycleCoroutineScope)
        .getProblemDao()

    val data:LiveData<List<ProblemWithAnswerAndHistory>> by lazy {
        dao.getAll()
    }

    fun insert(entity: QuestionProblemEntity) =
        viewModelScope.launch(Dispatchers.IO){
            dao.insert(entity = entity)
        }

    fun update(entity: QuestionProblemEntity) =
        viewModelScope.launch(Dispatchers.IO){
            dao.update(entity = entity)
        }

    fun delete(entity:QuestionProblemEntity) =
        viewModelScope.launch(Dispatchers.IO){
            dao.delete(entity = entity)
        }
}