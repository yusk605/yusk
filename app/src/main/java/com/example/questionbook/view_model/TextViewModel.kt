package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.*
import com.example.questionbook.room.QuestionDatabase
import com.example.questionbook.room.QuestionTextEntity
import com.example.questionbook.room.TextWithAnswer
import com.example.questionbook.room.TextWithAnswerAndHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TextViewModel(private val app:Application): AndroidViewModel(app) {

    private val dao = QuestionDatabase
        .getInstance(app,viewModelScope)
        .getTextDao()

    val data:LiveData<List<TextWithAnswer>> by lazy {
        dao.get()
    }

    fun insert(entity: QuestionTextEntity) =
        viewModelScope.launch(Dispatchers.IO){
            dao.insert(entity = entity)
        }

    fun update(entity: QuestionTextEntity) =
        viewModelScope.launch(Dispatchers.IO){
            dao.update(entity = entity)
        }

    fun delete(entity:QuestionTextEntity) =
        viewModelScope.launch(Dispatchers.IO){
            dao.delete(entity = entity)
        }
}