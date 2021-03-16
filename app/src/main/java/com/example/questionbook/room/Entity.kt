package com.example.questionbook.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_category")
data class QuestionCategory(
    @PrimaryKey(autoGenerate = true) val categoryNo:Int,
    @ColumnInfo(name = "category_title")var category:String
)

@Entity(tableName = "question_workbook")
data class QuestionWorkBook(
    @PrimaryKey(autoGenerate = true)val workBookNo:Int,
    @ColumnInfo(name = "workbook_title")val workBookTitle:String,
    @ColumnInfo(name = "workbook_text")val workBookText:String,
    @ColumnInfo(name = "workbook_accuracy")val answerAccuracyRate:Float,
    @ColumnInfo(name = "relation_category")val relationCategory:Int
)

@Entity(tableName = "question_answer")
data class QuestionAnswer(
    @PrimaryKey(autoGenerate = true)val answerNo:Int,
    @ColumnInfo(name = "answer_firs")val answerFirs:String,
    @ColumnInfo(name = "answer_second")val answerSecond:String,
    @ColumnInfo(name = "answer_third")val answerThird:String,
    @ColumnInfo(name = "answer_right")val answerRight:String,
    @ColumnInfo(name = "relation_workbook")val relationWorkBook:Int
)
