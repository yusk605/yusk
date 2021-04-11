package com.example.questionbook.room

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * ■カテゴリーの分類分けをするエンティティ
 *  @param categoryNo　  識別番号
 *  @param categoryTitle カテゴリーでのタイトル。
 *  @param categoryFlag　カテゴリーでのチェック項目。
 */
@Parcelize
@Entity(tableName = "question_category")
data class QuestionCategoryEntity(
    @PrimaryKey(autoGenerate = true)val categoryNo:Int,
    @ColumnInfo(name = "category_title")var categoryTitle:String,
    @ColumnInfo(name="category_flag")val categoryFlag:Int
):Parcelable

/**
 * ■問題集の分類分けをするエンティティ
 * @param workBookNo 識別番号
 * @param workBookTitle 問題集のタイトル
 * @param workBookDate timestamp 更新日：更新時間
 * @param workBookFlag 0..ホルダー、ゲーム表示可 1..ゲーム一覧非表示 2..削除候補
 * @param relationCategory question_category categoryNo 紐づけとなるナンバー
 */
@Parcelize
@Entity(tableName = "question_workbook")
data class QuestionWorkBookEntity(
    @PrimaryKey(autoGenerate = true)val workBookNo:Int,
    @ColumnInfo(name = "workbook_title")val workBookTitle:String,
    @ColumnInfo(name = "workbook_flag")val workBookFlag:Int,
    @ColumnInfo(name = "timestamp")val workBookDate:LocalDateTime,
    @ColumnInfo(name = "relation_category")val relationCategory:Int
):Parcelable

/**
 * ■回答率を保持するエンティティ
 * @param accuracyNo        識別番号
 * @param accuracyRate      正解率
 * @param accuracyDate      日付
 * @param relationWorkBook  question_workbook workBookNo 紐づけとなるナンバー
 */
@Parcelize
@Entity(tableName = "question_accuracy")
data class QuestionAccuracyEntity(
        @PrimaryKey(autoGenerate = true)val accuracyNo:Int,
        @ColumnInfo(name = "accuracy_rate")val accuracyRate:Float,
        @ColumnInfo(name = "accuracy_date")val accuracyDate:LocalDate,
        @ColumnInfo(name = "relation_workbook")val relationWorkBook: Int
):Parcelable

/**
 * ■問題のテキストとなるデータを表示
 * @param textNo         識別番号
 * @param textStatement  問題文
 * @param textFlag       0..ホルダー、ゲーム表示可 1..ゲーム一覧非表示  2..削除候補
 * @param timeStamp         更新日時
 * @param relationWorkBook  question_workbook workBookNo 紐づけとなるナンバー
 */
@Parcelize
@Entity(tableName = "question_text")
data class QuestionTextEntity(
        @PrimaryKey(autoGenerate = true)val textNo:Int,
        @ColumnInfo(name = "text_statement")val textStatement:String,
        @ColumnInfo(name = "text_flag")val textFlag:Int,
        @ColumnInfo(name = "timestamp")val timeStamp:LocalDateTime,
        @ColumnInfo(name = "relation_workbook")val relationWorkBook: Int
):Parcelable

/**
 * ■解答案を保存するためのエンティティ
 * @param answerNo        識別番号
 * @param answerFirs      解答案その1
 * @param answerSecond    解答案その2
 * @param answerThird     解答案その3
 * @param answerRight     解答案
 * @param relationText question_problem problemNo 紐づけるナンバー
 */
@Parcelize
@Entity(tableName = "question_answer")
data class QuestionAnswerEntity(
    @PrimaryKey(autoGenerate = true)val answerNo:Int,
    @ColumnInfo(name = "answer_firs")val answerFirs:String,
    @ColumnInfo(name = "answer_second")val answerSecond:String,
    @ColumnInfo(name = "answer_third")val answerThird:String,
    @ColumnInfo(name = "answer_right")val answerRight:String,
    @ColumnInfo(name = "relation_text")val relationText:Int
):Parcelable

/**
 * ■履歴を保存するためのエンティティ
 * @param historyNo         識別番号
 * @param historyRate       正解
 * @param relationText   問題集と紐づくナンバー
 * @param relationAccuracy  解答と紐づくナンバー
 */
@Parcelize
@Entity(tableName = "question_history")
data class QuestionHistoryEntity(
        @PrimaryKey(autoGenerate = true)val historyNo:Int,
        @ColumnInfo(name = "history_rate")val historyRate:Int,
        @ColumnInfo(name = "relation_text")val relationText:Int,
        @ColumnInfo(name = "relation_accuracy")val relationAccuracy:Int
):Parcelable

/**
 * カテゴリーテーブルの識別番号に紐づいた問題集一覧を取得します。
 */
@Parcelize
data class CategoryWithWorkBooks(
        @Embedded
        val questionCategoryEntity:QuestionCategoryEntity,
        @Relation(
                parentColumn = "categoryNo",
                entityColumn = "relation_category"
        )val workBookList:List<QuestionWorkBookEntity>
):Parcelable


/**
 * ワークブック（問題集）の識別番号に紐づいた、テキスト（問題）をすべて取得する。
 */
@Parcelize
data class WorkBookWithTextAndAccuracy(
        @Embedded
        val workBookEntity:QuestionWorkBookEntity,
        @Relation(
                parentColumn = "workBookNo",
                entityColumn = "relation_workbook", )
        val problemList:List<QuestionTextEntity>,
        @Relation(
                parentColumn = "workBookNo",
                entityColumn = "relation_workbook")
        val accuracyList:List<QuestionAccuracyEntity>
):Parcelable


/**
 *問題となるテキストの識別番号に紐づいた、回答欄すべてを取得する。
 */
@Parcelize
data class TextWithAnswerAndHistory(
        @Embedded
        val problemEntity: QuestionTextEntity,
        @Relation(
                parentColumn = "problemNo",
                entityColumn = "relation_problem"
        )val answer:QuestionAnswerEntity,
        @Relation(
            parentColumn = "problemNo",
            entityColumn = "relation_problem"
        )val history:QuestionHistoryEntity
):Parcelable

@Parcelize
data class AccuracyWithHistory(
        @Embedded
        val accuracyEntity: QuestionAccuracyEntity,
        @Relation(
                parentColumn = "accuracyNo",
                entityColumn = "relation_accuracy"
        )val historyList:List<QuestionHistoryEntity>
):Parcelable


