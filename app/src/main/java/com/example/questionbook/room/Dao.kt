package com.example.questionbook.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao    //カテゴリー
interface QuestionCategoryDao{
    @Query("select * from question_category") fun getAll():LiveData<List<CategoryWithWorkBooks>>
    @Insert suspend fun insert(entity:QuestionCategoryEntity)
    @Update suspend fun update(entity:QuestionCategoryEntity)
    @Delete suspend fun delete(entity:QuestionCategoryEntity)
}

@Dao    //問題集
interface QuestionWorkBookDao{
    @Query("select * from question_workbook")fun getAll():LiveData<List<WorkBookWithProblemsAndAccuracy>>
    @Insert suspend fun insert(entity:QuestionWorkBookEntity)
    @Update suspend fun update(entity:QuestionWorkBookEntity)
    @Delete suspend fun delete(entity:QuestionWorkBookEntity)
}

@Dao    //問題文
interface QuestionProblemDao{
    @Query("select * from question_problem")fun getAll():LiveData<List<ProblemWithAnswer>>
    @Insert suspend fun insert(entity:QuestionProblemEntity)
    @Update suspend fun update(entity:QuestionProblemEntity)
    @Delete suspend fun delete(entity:QuestionProblemEntity)

}

@Dao    //正解率
interface QuestionAccuracyDao{
    @Query("select * from question_answer")fun getAll():LiveData<List<QuestionAccuracyEntity>>
    @Insert suspend fun insert(entity:QuestionAccuracyEntity)
    @Update suspend fun update(entity:QuestionAccuracyEntity)
    @Delete suspend fun delete(entity:QuestionAccuracyEntity)
}

@Dao    //回答欄
interface QuestionAnswerDao{
    @Query("select * from question_answer")fun getAll():LiveData<List<QuestionAnswerEntity>>
    @Insert fun insert(entity:QuestionAnswerEntity)
    @Update fun update(entity:QuestionAnswerEntity)
    @Delete fun delete(entity:QuestionAnswerEntity)
}
