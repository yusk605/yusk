package com.example.questionbook.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionCategoryDao{
    @Query("select * from question_category")
    fun getAll():LiveData<List<QuestionCategory>>

    @Insert
    suspend fun insert(entity:QuestionCategory)

    @Update
    suspend fun update(entity:QuestionCategory)

    @Delete
    suspend fun delete(entity:QuestionCategory)

}

@Dao
interface QuestionWorkBookDao{
    @Query("select * from question_workbook")
    fun getAll():LiveData<List<QuestionWorkBook>>

    @Insert
    suspend fun insert(entity:QuestionWorkBook)

    @Update
    suspend fun update(entity:QuestionWorkBook)

    @Delete
    suspend fun delete(entity:QuestionWorkBook)
}

@Dao
interface QuestionAnswerDao{
    @Query("select * from question_answer")
    fun getAll():LiveData<List<QuestionAnswer>>

    @Insert
    fun insert(entity:QuestionAnswer)

    @Update
    fun update(entity:QuestionAnswer)

    @Delete
    fun delete(entity:QuestionAnswer)
}
