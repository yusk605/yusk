package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.questionbook.room.QuestionDatabase

class CategoryViewModelFactory(private val app: Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(CategoryViewModel::class.java))
            return CategoryViewModel(app) as T

        throw IllegalArgumentException("Type mismatch CategoryViewModel")
    }
}

class WorkBookViewModelFactory(private val app: Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(WorkBookViewModel::class.java))
            return WorkBookViewModel(app) as T

        throw IllegalArgumentException("Type mismatch WorkBookViewModel")
    }
}

class AccuracyViewModelFactory(private val app:Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AccuracyViewModel::class.java))
            return AccuracyViewModel(app) as T

        throw IllegalArgumentException("Type mismatch AccuracyViewModel")
    }
}

class AnswerViewModelFactory(private val database:QuestionDatabase):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AnswerViewModel::class.java))
            return AnswerViewModel(database.getAnswerDao()) as T

        throw IllegalArgumentException("Type mismatch AnswerViewModel")
    }
}

class ProblemViewModelFactory(private val app:Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ProblemViewModel::class.java))
            return ProblemViewModel(app) as T

        throw IllegalArgumentException("Type mismatch ProblemViewModel")
    }
}







