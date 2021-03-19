package com.example.questionbook.room

import android.app.Application
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@TypeConverters(Converter::class)
@Database(
    entities = [
        QuestionCategoryEntity::class,
        QuestionWorkBookEntity::class,
        QuestionProblemEntity::class,
        QuestionAccuracyEntity::class,
        QuestionAnswerEntity::class ] ,
    version = 1)
abstract class QuestionDatabase:RoomDatabase() {

    abstract fun getCategoryDao():QuestionCategoryDao
    abstract fun getWorkBookDao():QuestionWorkBookDao
    abstract fun getProblemDao():QuestionProblemDao
    abstract fun getAccuracyDao():QuestionAccuracyDao
    abstract fun getAnswerDao():QuestionAnswerDao

    companion object{

        var singleton:QuestionDatabase? = null

        fun getInstance(application: Application,scope: LifecycleCoroutineScope):QuestionDatabase =
            singleton ?: synchronized(this){
                val instance = Room
                    .databaseBuilder(application,QuestionDatabase::class.java,"question_book")
                    //.addCallback(QuestionDBCallBack(scope))
                    .build()
                singleton = instance
                instance
            }

        class QuestionDBCallBack(private val scope: LifecycleCoroutineScope):RoomDatabase.Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                singleton?.let { database->
                    scope.launch(Dispatchers.IO) {
                        // TODO データベースの初期処理を記述すること。
                    }
                }
            }
        }
    }
}