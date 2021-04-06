package com.example.questionbook.room

import android.app.Application
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@TypeConverters(Converter::class)
@Database(entities = [
    QuestionCategoryEntity::class,
    QuestionWorkBookEntity::class,
    QuestionProblemEntity::class,
    QuestionAccuracyEntity::class,
    QuestionAnswerEntity::class,
    QuestionHistoryEntity::class
                     ],version = 1)
abstract class QuestionDatabase:RoomDatabase() {

    abstract fun getCategoryDao():QuestionCategoryDao
    abstract fun getWorkBookDao():QuestionWorkBookDao
    abstract fun getProblemDao():QuestionProblemDao
    abstract fun getAccuracyDao():QuestionAccuracyDao
    abstract fun getAnswerDao():QuestionAnswerDao
    abstract fun getHistoryDao():QuestionHistoryDao

    companion object{

        private var singleton:QuestionDatabase? = null

        fun getInstance(application:Application, scope:CoroutineScope):QuestionDatabase =
            singleton ?: synchronized(this){
                val instance = Room
                    .databaseBuilder(application,QuestionDatabase::class.java,"question_book")
                    //.addCallback(QuestionDBCallBack(scope))
                    .build()
                singleton = instance
                instance
            }

        /**
         *多分エラーが発生します。
         */
        class QuestionDBCallBack(private val scope:CoroutineScope):RoomDatabase.Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                singleton?.let { database->
                    scope.launch(Dispatchers.IO) {
                        database.run {
                            getWorkBookDao().collBackDelete()
                            getProblemDao().collBackDelete()
                            getAccuracyDao().collBackDelete()
                            getAnswerDao().collBackDelete()
                        }
                    }
                }
            }
        }
    }
}