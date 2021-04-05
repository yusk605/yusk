package com.example.questionbook.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao    //カテゴリー
interface QuestionCategoryDao{
    @Query("select * from question_category")fun getAll():LiveData<List<CategoryWithWorkBooks>>
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

    @Transaction
    @Query("delete from question_workbook where relation_category in (" +
            "select relation_category from question_workbook left join question_category on relation_category = categoryNo where categoryNo is null)")
    suspend fun collBackDelete()

}

@Dao    //問題文
interface QuestionProblemDao{
    @Query("select * from question_problem")fun getAll():LiveData<List<ProblemWithAnswerAndHistory>>
    @Insert suspend fun insert(entity:QuestionProblemEntity)
    @Update suspend fun update(entity:QuestionProblemEntity)
    @Delete suspend fun delete(entity:QuestionProblemEntity)

    @Transaction
    @Query("delete from question_problem where relation_workbook in ( " +
            "select relation_workbook from question_problem left join question_workbook on relation_workbook = workBookNo where workBookNo is null)")
    suspend fun collBackDelete()
}

@Dao    //正解率
interface QuestionAccuracyDao{
    @Query("select * from question_accuracy")fun getAll():LiveData<List<AccuracyWithHistory>>

    @Insert suspend fun insert(entity:QuestionAccuracyEntity)
    @Update suspend fun update(entity:QuestionAccuracyEntity)
    @Delete suspend fun delete(entity:QuestionAccuracyEntity)

    @Transaction
    @Query("delete from question_accuracy where relation_workbook in (" +
            " select relation_workbook from question_accuracy left join question_workbook on relation_workbook = workBookNo where workBookNo is null)")
    suspend fun collBackDelete()
}

@Dao    //回答欄
interface QuestionAnswerDao{
    @Query("select * from question_answer")fun getAll():LiveData<List<QuestionAnswerEntity>>
    @Insert fun insert(entity:QuestionAnswerEntity)
    @Update fun update(entity:QuestionAnswerEntity)
    @Delete fun delete(entity:QuestionAnswerEntity)

    @Transaction
    @Query("delete from question_answer where relation_problem in ( " +
            "select relation_problem from question_answer left join question_problem on relation_problem = problemNo where answerNo is null)")
    suspend fun collBackDelete()
}


@Dao    //履歴の表示
interface QuestionHistoryDao{
    @Query("select * from question_history")fun getAll():LiveData<List<QuestionHistoryEntity>>
    @Insert fun insert(entity:QuestionHistoryEntity)
    @Update fun update(entity:QuestionHistoryEntity)
    @Delete fun delete(entity:QuestionHistoryEntity)

}
