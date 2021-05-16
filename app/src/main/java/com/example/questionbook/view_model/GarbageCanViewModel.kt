package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.questionbook.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GarbageCanViewModel(private val app:Application):AndroidViewModel(app) {

    private val db = QuestionDatabase.getInstance(app,viewModelScope)

    private val categoryDao:QuestionCategoryDao
    private val workBookDao:QuestionWorkBookDao
    private val leafDao:QuestionLeafDao

    init {
        db.run {
            categoryDao = getCategoryDao()
            workBookDao = getWorkBookDao()
            leafDao     = getLeafDao()
        }
    }

    private var _categoryList = categoryDao.getList()
    private var _workBookList = workBookDao.getList()
    private var _leafList     = leafDao.getList()

    val categoryList get() = _categoryList
    val workBookList get() = _workBookList
    val leafList     get() = _leafList

    fun deleteCategory(entity: QuestionCategoryEntity){
        viewModelScope.launch(Dispatchers.IO) {
            categoryDao.delete(entity)
        }
    }

    fun upDateCategory(entity: QuestionCategoryEntity){
        viewModelScope.launch(Dispatchers.IO) {
            categoryDao.update(entity)
        }
    }

    fun deleteWorkBook(entity:QuestionWorkBookEntity){
        viewModelScope.launch(Dispatchers.IO) {
            workBookDao.delete(entity)
        }
    }
    fun upDateWorkBook(entity:QuestionWorkBookEntity){
        viewModelScope.launch(Dispatchers.IO) {
            workBookDao.update(entity)
        }
    }

    fun deleteLeaf(entity:QuestionLeafEntity){
        viewModelScope.launch(Dispatchers.IO) {
            leafDao.delete(entity)
        }
    }
    fun upDateLeaf(entity:QuestionLeafEntity){
        viewModelScope.launch(Dispatchers.IO) {
            leafDao.update(entity)
        }
    }
}