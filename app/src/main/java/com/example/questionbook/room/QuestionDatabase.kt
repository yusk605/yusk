package com.example.questionbook.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@TypeConverters(Converter::class)
@Database(entities = [
    QuestionCategoryEntity::class,
    QuestionWorkBookEntity::class,
    QuestionTextEntity::class,
    QuestionAccuracyEntity::class,
    QuestionAnswerEntity::class,
    QuestionHistoryEntity::class
                     ],version = 1)
abstract class QuestionDatabase:RoomDatabase() {

    abstract fun getCategoryDao():QuestionCategoryDao
    abstract fun getWorkBookDao():QuestionWorkBookDao
    abstract fun getTextDao():QuestionTextDao
    abstract fun getAccuracyDao():QuestionAccuracyDao
    abstract fun getAnswerDao():QuestionAnswerDao
    abstract fun getHistoryDao():QuestionHistoryDao

    companion object{

        private var singleton:QuestionDatabase? = null

        fun getInstance(application:Application, scope:CoroutineScope):QuestionDatabase =
            singleton ?: synchronized(this){
                val instance = Room
                    .databaseBuilder(application,QuestionDatabase::class.java,"question_book")
                   // .addCallback(QuestionDBInsertTestCallBack(scope))
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
                            getTextDao().collBackDelete()
                            getAccuracyDao().collBackDelete()
                            getAnswerDao().collBackDelete()
                        }
                    }
                }
            }
        }

        /**
         * テスト用の初期表示クラス
         */
        class QuestionDBInsertTestCallBack(private val scope: CoroutineScope):RoomDatabase.Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                singleton?.let { database ->
                    scope?.launch(Dispatchers.IO) {
                        initInsertEntity(database, scope)
                    }
                }
            }

            /**
             * 初期化時にデータを入れる。
             */
            private fun initInsertEntity(database: QuestionDatabase,scope:CoroutineScope){
                (0..10).forEach {
                    scope.launch(Dispatchers.IO){
                       /* database.getCategoryDao().insert(
                                QuestionCategoryEntity(
                                        categoryNo = 0,
                                        categoryTitle = "CategoryTest${it}",
                                        categoryFlag = 0
                                )
                        )
                        database.getWorkBookDao().insert(
                                QuestionWorkBookEntity(
                                        workBookNo = 0,
                                        workBookTitle = "WorkbookTitle${it}",
                                        workBookDate = LocalDateTime.now(),
                                        workBookFlag = 0,
                                        relationCategory = 1
                                )
                        )
                        database.getTextDao().insert(
                                QuestionTextEntity(
                                        textNo = 0,
                                        textStatement = "問題文問題文問題文問題文問題文問題文問題文問題文",
                                        textFlag = 0,
                                        timeStamp = LocalDateTime.now(),
                                        relationWorkBook =1
                                )
                        )*/
                        database.getAnswerDao().insert(
                            QuestionAnswerEntity(
                                answerNo = 0,
                                answerFirs = "選択案その一",
                                answerSecond = "選択案その2",
                                answerThird = "選択案その3",
                                answerRight = "正解となる案",
                                relationText = 0
                            )
                        )
                    }
                }
            }
        }
    }
}