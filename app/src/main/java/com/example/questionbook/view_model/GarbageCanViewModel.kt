package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.questionbook.room.QuestionCategoryDao
import com.example.questionbook.room.QuestionDatabase
import com.example.questionbook.room.QuestionLeafDao
import com.example.questionbook.room.QuestionWorkBookDao

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
}