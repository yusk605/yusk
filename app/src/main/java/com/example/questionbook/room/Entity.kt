package com.example.questionbook.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import java.time.LocalDate
import java.time.LocalDateTime

//カテゴリーテーブル
@Entity(tableName = "question_category")
data class QuestionCategoryEntity(
    @PrimaryKey(autoGenerate = true)val categoryNo:Int,
    @ColumnInfo(name = "category_title")var categoryTitle:String,
    @ColumnInfo(name="category_flag")val categoryFlag:Int
)

//問題集テーブル
@Entity(tableName = "question_workbook")
data class QuestionWorkBookEntity(
    @PrimaryKey(autoGenerate = true)val workBookNo:Int,
    @ColumnInfo(name = "workbook_title")val workBookTitle:String,
    @ColumnInfo(name = "workbook_flag")val workBookFlag:Int,
    @ColumnInfo(name = "timestamp")val workBookDate:LocalDateTime,
    @ColumnInfo(name = "relation_category")val relationCategory:Int
)

//正解率テーブル
@Entity(tableName = "question_accuracy")
data class QuestionAccuracyEntity(
        @PrimaryKey(autoGenerate = true)val accuracyNo:Int,
        @ColumnInfo(name = "accuracy_rate")val accuracyRate:Float,
        @ColumnInfo(name = "accuracy_savedate")val accuracySaveDate:LocalDate,
        @ColumnInfo(name = "relation_workbook")val relationWorkBook: Int
)

//問題文テーブル
@Entity(tableName = "question_problem")
data class QuestionProblemEntity(
    @PrimaryKey(autoGenerate = true)val problemNo:Int,
    @ColumnInfo(name = "problem_statement")val problemStatement:String,
    @ColumnInfo(name = "problem_flag")val problemFlag:Int,
    @ColumnInfo(name = "timestamp")val timeStamp:LocalDateTime,
    @ColumnInfo(name = "relation_workbook")val relationWorkBook: Int
)

//回答欄テーブル
@Entity(tableName = "question_answer")
data class QuestionAnswerEntity(
    @PrimaryKey(autoGenerate = true)val answerNo:Int,
    @ColumnInfo(name = "answer_firs")val answerFirs:String,
    @ColumnInfo(name = "answer_second")val answerSecond:String,
    @ColumnInfo(name = "answer_third")val answerThird:String,
    @ColumnInfo(name = "answer_right")val answerRight:String,
    @ColumnInfo(name = "relation_problem")val relationWorkBook:Int
)


/**
 * カテゴリーテーブルのプライマリキー（識別番号）
 * に紐づいた問題集一覧を取得します。
 */
data class CategoryWithWorkBooks(
        @Embedded
        val questionCategoryEntity:QuestionCategoryEntity,
        @Relation(
                parentColumn = "categoryNo",
                entityColumn = "relation_category"
        )val workBooks:List<QuestionWorkBookEntity>
)
/**
 * ワークブック（問題集）の識別番号に紐づいた、
 * テキスト（問題）をすべて取得する。
 */
data class WorkBookWithProblems(
        @Embedded
        val workBookEntity:QuestionWorkBookEntity,
        @Relation(
                parentColumn = "workBookNo",
                entityColumn = "relation_workbook",
        )val problems:List<QuestionProblemEntity>
)

/**
 *問題となるテキストの識別番号に紐づいた、
 *回答欄すべてを取得する。
 */
data class ProblemWithAnswer(
        @Embedded
        val problemEntity: QuestionProblemEntity,
        @Relation(
                parentColumn = "problemNo",
                entityColumn = "relation_problem"
        )val answers:List<QuestionAnswerEntity>
)
