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


    fun deleteMutableCategory(data:List<QuestionCategoryEntity>) =
        viewModelScope.launch(Dispatchers.IO) {
            data.forEach {
                categoryDao.delete(it)
            }
        }

    fun deleteMutableWorkBook(data:List<QuestionWorkBookEntity>) =
        viewModelScope.launch(Dispatchers.IO) {
            data.forEach {
                workBookDao.delete(it)
            }
        }

    fun deleteMutableLeafList(data:List<QuestionLeafEntity>) =
        viewModelScope.launch(Dispatchers.IO) {
            data.forEach {
                leafDao.delete(it)
            }
        }
    }