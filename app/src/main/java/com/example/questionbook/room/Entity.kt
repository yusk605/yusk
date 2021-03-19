package com.example.questionbook.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

//カテゴリーテーブル
@Entity(tableName = "question_category")
data class QuestionCategory(
    @PrimaryKey(autoGenerate = true)val categoryNo:Int,
    @ColumnInfo(name = "category_title")var categoryTitle:String,
    @ColumnInfo(name="category_flag")val categoryFlag:Int
)

//問題集テーブル
@Entity(tableName = "question_workbook")
data class QuestionWorkBook(
    @PrimaryKey(autoGenerate = true)val workBookNo:Int,
    @ColumnInfo(name = "workbook_title")val workBookTitle:String,
    @ColumnInfo(name = "workbook_flag")val workBookFlag:Int,
    @ColumnInfo(name = "timestamp")val workBookDate:LocalDateTime,
    @ColumnInfo(name = "relation_category")val relationCategory:Int
)

//正解率テーブル
@Entity(tableName = "question_accuracy")
data class QuestionAccuracy(
        @PrimaryKey(autoGenerate = true)val accuracyNo:Int,
        @ColumnInfo(name = "accuracy_rate")val accuracyRate:Float,
        @ColumnInfo(name = "accuracy_savedate")val accuracySaveDate:LocalDate,
        @ColumnInfo(name = "relation_workbook")val relationWorkBook: Int
)

//問題文テーブル
@Entity(tableName = "question_problem")
data class QuestionProblem(
    @PrimaryKey(autoGenerate = true)val problemNo:Int,
    @ColumnInfo(name = "problem_statement")val problemStatement:String,
    @ColumnInfo(name = "problem_flag")val problemFlag:Int,
    @ColumnInfo(name = "timestamp")val timeStamp:LocalDateTime,
    @ColumnInfo(name = "relation_workbook")val relationWorkBook: Int
)

//回答欄テーブル
@Entity(tableName = "question_answer")
data class QuestionAnswer(
    @PrimaryKey(autoGenerate = true)val answerNo:Int,
    @ColumnInfo(name = "answer_firs")val answerFirs:String,
    @ColumnInfo(name = "answer_second")val answerSecond:String,
    @ColumnInfo(name = "answer_third")val answerThird:String,
    @ColumnInfo(name = "answer_right")val answerRight:String,
    @ColumnInfo(name = "relation_problem")val relationWorkBook:Int
)
