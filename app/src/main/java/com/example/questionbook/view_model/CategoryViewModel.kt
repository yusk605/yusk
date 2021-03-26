package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.*
import com.example.questionbook.room.CategoryWithWorkBooks
import com.example.questionbook.room.QuestionCategoryDao
import com.example.questionbook.room.QuestionCategoryEntity
import com.example.questionbook.room.QuestionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CategoryViewModel(private val app: Application):AndroidViewModel(app) {

    private val dao = QuestionDatabase.getInstance(app, viewModelScope as LifecycleCoroutineScope).getCategoryDao()
    private var _data:LiveData<List<CategoryWithWorkBooks>> = dao.getAll()

    val data get() = _data

    fun insert(entity:QuestionCategoryEntity) =
        viewModelScope.launch(Dispatchers.IO){
            dao.insert(entity)
        }

    fun update(entity:QuestionCategoryEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(entity)
        }

    fun delete(entity:QuestionCategoryEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(entity)
        }
}