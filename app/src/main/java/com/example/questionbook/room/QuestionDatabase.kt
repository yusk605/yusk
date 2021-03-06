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
    QuestionAccuracyEntity::class,
    QuestionHistoryEntity::class,
    QuestionLeafEntity::class
                     ],version = 1)
abstract class QuestionDatabase:RoomDatabase(){

    abstract fun getCategoryDao():QuestionCategoryDao
    abstract fun getWorkBookDao():QuestionWorkBookDao
    abstract fun getAccuracyDao():QuestionAccuracyDao
    abstract fun getHistoryDao():QuestionHistoryDao
    abstract fun getLeafDao():QuestionLeafDao

    companion object{

        private var singleton:QuestionDatabase? = null

        fun getInstance(application:Application, scope:CoroutineScope):QuestionDatabase =
            singleton ?: synchronized(this){
                val instance = Room
                        .databaseBuilder(application,QuestionDatabase::class.java,"question_book_database")
                        .addCallback(QuestionDBCallBack(scope))
                        .fallbackToDestructiveMigration()
                        .build()
                singleton = instance
                instance
            }

        /**
         * ▪孤立したエンティティの削除を行う
         * リレーションされていないワークブックエンティティデータを削除する
         */
        class QuestionDBCallBack(private val scope:CoroutineScope):RoomDatabase.Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                singleton?.let { database->
                    scope.launch(Dispatchers.IO) {
                        database.run {
                            getWorkBookDao().collBackDelete()
                            getAccuracyDao().collBackDelete()
                            getLeafDao().collBackDelete()
                            getHistoryDao().collBackDelete()
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
                        if (database.getCategoryDao().get().isEmpty()) {
                            if (it < 3) {
                                database.getCategoryDao().insert(
                                    QuestionCategoryEntity(
                                        categoryNo = 0,
                                        categoryTitle = "CategoryTest${it}",
                                        categoryFlag = 0,
                                        timeStamp = LocalDateTime.now()
                                    )
                                )
                            }
                            if (it < 3) {
                                database.getWorkBookDao().insert(
                                    QuestionWorkBookEntity(
                                        workBookNo = 0,
                                        workBookTitle = "WorkbookTitle${it}",
                                        timeStamp = LocalDateTime.now(),
                                        workBookFlag = 0,
                                        relationCategory = 1
                                    )
                                )
                            }
                            database.getLeafDao().insert(
                                entity = QuestionLeafEntity(
                                    leafNo = 0,
                                    leafFlag = 0,
                                    leafCommentary = "",
                                    leafFirs = "問題1",
                                    leafSecond = "問題2",
                                    leafThird = "問題3",
                                    leafRight = "正解",
                                    leafStatement = "問題文問題文文字列を問題文問題文問題文",
                                    timeStamp = LocalDateTime.now(),
                                    relationWorkBook = 1
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }