package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.*
import com.example.questionbook.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TextViewModel(private val app:Application): AndroidViewModel(app) {

    private val textDao = QuestionDatabase.getInstance(app,viewModelScope).getTextDao()

    private val answerDao = QuestionDatabase.getInstance(app,viewModelScope).getAnswerDao()

    val data:LiveData<List<TextWithAnswer>> by lazy { textDao.get() }


    fun insertAnswer(entity:QuestionAnswerEntity){
        viewModelScope.launch(Dispatchers.IO) {
            answerDao.insert(entity= entity)
        }
    }

    fun updateAnswer(entity: QuestionAnswerEntity){
        viewModelScope.launch(Dispatchers.IO) {
            answerDao.update(entity = entity)
        }
    }

    fun textInsert(entity: QuestionTextEntity) =
        viewModelScope.launch(Dispatchers.IO){
            textDao.insert(entity = entity)
        }

    fun update(entity: QuestionTextEntity) =
        viewModelScope.launch(Dispatchers.IO){
            textDao.update(entity = entity)
        }

    fun delete(entity:QuestionTextEntity) =
        viewModelScope.launch(Dispatchers.IO){
            textDao.delete(entity = entity)
        }
}