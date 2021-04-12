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
    @Query("select * from question_workbook")fun getAll():LiveData<List<WorkBookWithTextAndAccuracy>>
    @Insert suspend fun insert(entity:QuestionWorkBookEntity)
    @Update suspend fun update(entity:QuestionWorkBookEntity)
    @Delete suspend fun delete(entity:QuestionWorkBookEntity)

    @Query("delete from question_workbook where relation_category in (" +
            "select relation_category from question_workbook left join question_category on relation_category = categoryNo where categoryNo is null)")
    suspend fun collBackDelete()

}

@Dao
interface QuestionTextDao{
    @Query("select * from question_text")fun get():LiveData<List<TextWithAnswer>>
    @Query("select * from question_text")fun getAll():LiveData<List<TextWithAnswerAndHistory>>
    @Insert suspend fun insert(entity:QuestionTextEntity)
    @Update suspend fun update(entity:QuestionTextEntity)
    @Delete suspend fun delete(entity:QuestionTextEntity)

    @Query("delete from question_text where relation_workbook in ( " +
            "select relation_workbook from question_text left join question_workbook on relation_workbook = workBookNo where workBookNo is null)")
    suspend fun collBackDelete()
}

@Dao
interface QuestionAccuracyDao{
    @Query("select * from question_accuracy")fun getAll():LiveData<List<AccuracyWithHistory>>
    @Insert suspend fun insert(entity:QuestionAccuracyEntity)
    @Update suspend fun update(entity:QuestionAccuracyEntity)
    @Delete suspend fun delete(entity:QuestionAccuracyEntity)

    @Query("delete from question_accuracy where relation_workbook in (" +
            " select relation_workbook from question_accuracy left join question_workbook on relation_workbook = workBookNo where workBookNo is null)")
    suspend fun collBackDelete()
}

@Dao
interface QuestionAnswerDao{
    @Query("select * from question_answer")fun getAll():LiveData<List<QuestionAnswerEntity>>
    @Insert fun insert(entity:QuestionAnswerEntity)
    @Update fun update(entity:QuestionAnswerEntity)
    @Delete fun delete(entity:QuestionAnswerEntity)

    @Query("delete from question_answer where relation_text in ( " +
            "select relation_text from question_answer left join question_text on relation_text = textNo where answerNo is null)")
    suspend fun collBackDelete()
}


@Dao
interface QuestionHistoryDao{
    @Query("select * from question_history")fun getAll():LiveData<List<QuestionHistoryEntity>>
    @Insert fun insert(entity:QuestionHistoryEntity)
    @Update fun update(entity:QuestionHistoryEntity)
    @Delete fun delete(entity:QuestionHistoryEntity)

}
