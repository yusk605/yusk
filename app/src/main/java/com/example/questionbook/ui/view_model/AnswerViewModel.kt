package com.example.questionbook.ui.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.questionbook.room.QuestionAnswerDao

class AnswerViewModel(private val dao:QuestionAnswerDao): ViewModel() {

}