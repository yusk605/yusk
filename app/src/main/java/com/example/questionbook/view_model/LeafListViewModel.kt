package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.questionbook.room.QuestionDatabase
import com.example.questionbook.room.QuestionLeafEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LeafListViewModel(application: Application):AndroidViewModel(application) {

    private val leafDao = QuestionDatabase.getInstance(application,viewModelScope).getQuizDao()

    private var _leafEntityList = leafDao.getList()

    val leafEntityList:LiveData<List<QuestionLeafEntity>>
        get() = _leafEntityList


    fun leafInsert(entity: QuestionLeafEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                leafDao.insert( entity = entity )
            }

    fun  leafUpdate(entity: QuestionLeafEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                leafDao.update( entity = entity)
            }
    fun leafDelete(entity: QuestionLeafEntity) =
            viewModelScope.launch(Dispatchers.IO) {
                leafDao.delete(entity = entity)
            }

}