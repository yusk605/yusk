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

class HistoryViewModelFactory(private val app:Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HistoryViewModel::class.java))
            return HistoryViewModel(app) as T

        throw IllegalArgumentException("Type mismatch HistoryViewModel")
    }
}

class QuizGameViewModelFactory(private val app: Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(QuizGameViewModel::class.java))
            return QuizGameViewModel(app) as T

        throw IllegalArgumentException("Type mismatch QuestionViewModelFactory")
    }
}


class LeafListViewModelFactory(private val app: Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeafListViewModel::class.java))
            return LeafListViewModel(app) as T

        throw IllegalArgumentException("Type mismatch QuizViewModel")
    }
}

class ResultViewModelFactory(private val app: Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java))
            return ResultViewModel(app) as T

        throw IllegalArgumentException("Type mismatch QuizViewModel")
    }
}










