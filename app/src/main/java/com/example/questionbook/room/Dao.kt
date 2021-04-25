package com.example.questionbook.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionCategoryDao{
    @Query("select * from question_category")fun getAll():LiveData<List<CategoryWithWorkBooks>>
    @Insert suspend fun insert(entity:QuestionCategoryEntity)
    @Update suspend fun update(entity:QuestionCategoryEntity)
    @Delete suspend fun delete(entity:QuestionCategoryEntity)
}

@Dao
interface QuestionWorkBookDao{
    @Query("select * from question_workbook")fun getList():LiveData<List<WorkBookWithTextAndAccuracy>>
    @Query("select * from question_workbook")fun getAll():LiveData<List<WorkBookWithAll>>
    @Insert suspend fun insert(entity:QuestionWorkBookEntity)
    @Update suspend fun update(entity:QuestionWorkBookEntity)
    @Delete suspend fun delete(entity:QuestionWorkBookEntity)

    @Query("delete from question_workbook where relation_category in (" +
            "select relation_category from question_workbook left join question_category on relation_category = categoryNo where categoryNo is null)")
    suspend fun collBackDelete()

}

@Dao
interface QuestionTextDao{
    @Query("select * from question_text")fun getList():LiveData<List<TextWithAnswer>>
    @Insert suspend fun insert(entity:QuestionTextEntity)
    @Update suspend fun update(entity:QuestionTextEntity)
    @Delete suspend fun delete(entity:QuestionTextEntity)

    @Query("delete from question_text where relation_workbook in ( " +
            "select relation_workbook from question_text left join question_workbook on relation_workbook = workBookNo where workBookNo is null)")
    suspend fun collBackDelete()
}

@Dao
interface QuestionAccuracyDao{
    @Query("select * from question_accuracy")fun getList():LiveData<List<QuestionAccuracyEntity>>
    @Insert suspend fun insert(entity:QuestionAccuracyEntity)
    @Update suspend fun update(entity:QuestionAccuracyEntity)
    @Delete suspend fun delete(entity:QuestionAccuracyEntity)

    @Query("delete from question_accuracy where relation_workbook in (" +
            " select relation_workbook from question_accuracy left join question_workbook on relation_workbook = workBookNo where workBookNo is null)")
    suspend fun collBackDelete()
}

@Dao
interface QuestionAnswerDao{
    @Query("select * from question_answer")fun getList():LiveData<List<QuestionAnswerEntity>>
    @Insert fun insert(entity:QuestionAnswerEntity)
    @Update fun update(entity:QuestionAnswerEntity)
    @Delete fun delete(entity:QuestionAnswerEntity)

}


@Dao
interface QuestionQuizDao{
    @Query("select * from question_quiz")fun getList():LiveData<List<QuestionQuizEntity>>
    @Insert suspend fun insert(entity:QuestionQuizEntity)
    @Update suspend fun update(entity:QuestionQuizEntity)
    @Delete suspend fun delete(entity:QuestionQuizEntity)
}

@Dao
interface QuestionHistoryDao{
    @Query("select * from question_history")fun getAll():LiveData<List<QuestionHistoryEntity>>
    @Insert fun insert(entity:QuestionHistoryEntity)
    @Update fun update(entity:QuestionHistoryEntity)
    @Delete fun delete(entity:QuestionHistoryEntity)

}
