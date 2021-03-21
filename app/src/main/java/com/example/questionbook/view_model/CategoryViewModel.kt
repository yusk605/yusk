package com.example.questionbook.view_model

import androidx.lifecycle.ViewModel
import com.example.questionbook.room.QuestionCategoryDao

class CategoryViewModel(private val dao:QuestionCategoryDao):ViewModel() {}