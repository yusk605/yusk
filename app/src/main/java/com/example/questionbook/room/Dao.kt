package com.example.questionbook.room

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

// ■ 項目でのヒエラルキーの頂点となるテーブル（カテゴリー）
@Dao
interface QuestionCategoryDao{
    @Transaction
    @Query("select * from question_category")
    fun getAll():LiveData<List<CategoryWithWorkBooks>>

    @Query("select * from question_category")
    fun getList():LiveData<List<QuestionCategoryEntity>>

    @Query("select * from question_category limit 1")
    suspend fun get():List<QuestionCategoryEntity>

    @Insert
    suspend fun insert(entity:QuestionCategoryEntity)

    @Update
    suspend fun update(entity:QuestionCategoryEntity)

    @Delete
    suspend fun delete(entity:QuestionCategoryEntity)
}

// ■ 項目での二番目の階層となるテーブル（問題集）
@Dao
interface QuestionWorkBookDao{
    @Transaction
    @Query("select * from question_workbook")
    fun getAll():LiveData<List<WorkBookWithAll>>

    @Transaction
    @Query("select * from question_workbook")
    fun getList():LiveData<List<QuestionWorkBookEntity>>

    @Insert suspend fun insert(entity:QuestionWorkBookEntity)
    @Update suspend fun update(entity:QuestionWorkBookEntity)
    @Delete suspend fun delete(entity:QuestionWorkBookEntity)

    @Transaction
    @Query("delete from question_workbook " +
            "where relation_category in (" +
            "select relation_category " +
            "from question_workbook " +
            "left join question_category " +
            "on relation_category = categoryNo " +
            "where categoryNo is null)")
    suspend fun collBackDelete()
}

// ■ 問題集に紐づいた回答率を保存するテーブル（回答率）
@Dao
interface QuestionAccuracyDao{

    @Query("select * from question_accuracy")fun getList():LiveData<List<QuestionAccuracyEntity>>

    @Transaction
    @Query("select * from question_accuracy")
    fun getAll():LiveData<List<AccuracyWithHistory>>

    @Insert
    suspend fun insert(entity:QuestionAccuracyEntity)

    @Update
    suspend fun update(entity:QuestionAccuracyEntity)

    @Delete
    suspend fun delete(entity:QuestionAccuracyEntity)

    @Insert
    fun insertRx(entity: QuestionAccuracyEntity):Completable

    @Query("select * from question_accuracy where accuracyNo = last_insert_rowid();")
    fun getLast():List<QuestionAccuracyEntity>

    @Transaction
    @Query("delete from question_accuracy " +
            "where relation_workbook in (" +
            "select relation_workbook " +
            "from question_accuracy " +
            "left join question_workbook " +
            "on relation_workbook = workBookNo " +
            "where workBookNo is null)")
    suspend fun collBackDelete()
}

// ■ 問題集に紐づいたクイズを保存するためのテーブル（クイズ）
@Dao
interface QuestionLeafDao{
    @Query("select * from question_leaf")fun getList():LiveData<List<QuestionLeafEntity>>

    @Transaction
    @Query("select * from question_leaf")
    fun getAll():LiveData<List<LeafWithHistory>>

    @Insert suspend fun insert(entity:QuestionLeafEntity)
    @Update suspend fun update(entity:QuestionLeafEntity)
    @Delete suspend fun delete(entity:QuestionLeafEntity)

    @Transaction
    @Query("delete from question_leaf " +
            "where relation_workbook in(" +
            "select relation_workbook " +
            "from question_leaf " +
            "left join question_workbook " +
            "on relation_workbook = workBookNo " +
            "where workBookNo is null)")
    suspend fun collBackDelete()
}

/*
  回答率のテーブルに紐づいた履歴となるデータを保存する
  クイズのテーブルと紐づいている理由としては、正誤判定
  各クイズの正誤判定を履歴として保持させたいため。
 */
@Dao
interface QuestionHistoryDao{

    @Query("select * from question_history")
    fun getList():LiveData<List<QuestionHistoryEntity>>

    @Transaction
    @Query("select * from question_leaf")
    fun getWithLeaf():LiveData<List<LeafWithHistory>>

    @Transaction
    @Query("select * from question_accuracy")
    fun getWithAccuracy():LiveData<List<AccuracyWithHistory>>

    @Insert fun insert(entity:QuestionHistoryEntity)
    @Update fun update(entity:QuestionHistoryEntity)
    @Delete fun delete(entity:QuestionHistoryEntity)

    @Transaction
    @Query("delete from question_history " +
            "where relation_accuracy in(" +
            "select relation_accuracy " +
            "from question_history " +
            "left join question_accuracy " +
            "on relation_accuracy = accuracyNo " +
            "where accuracyNo is null)")
    suspend fun collBackDelete()
}
