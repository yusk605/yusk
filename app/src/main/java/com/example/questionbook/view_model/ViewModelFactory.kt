package com.example.questionbook.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

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

class QuestionViewModelFactory(private val app: Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(QuestionFormViewModel::class.java))
            return QuestionFormViewModel(app) as T

        throw IllegalArgumentException("Type mismatch QuestionViewModelFactory")
    }
}

class ProblemViewModelFactory(private val app:Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ProblemViewModel::class.java))
            return ProblemViewModel(app) as T

        throw IllegalArgumentException("Type mismatch ProblemViewModel")
    }
}







