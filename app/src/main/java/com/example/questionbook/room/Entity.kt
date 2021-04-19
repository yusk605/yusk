package com.example.questionbook.room

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * ■カテゴリーの分類分けをするエンティティ
 *  @param categoryNo　   識別番号
 *  @param categoryTitle  カテゴリーでのタイトル。
 *  @param categoryFlag　 0..ホルダー、ゲーム表示可 1..ゲーム一覧非表示  2..削除候補　
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
 * ■解答案を保存するためのエンティティ
 * @param quizNo                識別番号
 * @param quizStatement         問題文
 * @param quizFirs              解答案その1
 * @param quizSecond            解答案その2
 * @param quizThird             解答案その3
 * @param quizRight             解答案
 * @param quizAnswerCheck       解答の値
 * @param quizCommentary        解答案
 * @param timeStamp             更新時間
 * @param relationWorkBook      問題集に紐づける番号
 */
@Parcelize
@Entity(tableName = "question_quiz")
data class QuestionQuizEntity(
        @PrimaryKey(autoGenerate = true)val quizNo:Int,
        @ColumnInfo(name = "quiz_statement")val quizStatement:String,
        @ColumnInfo(name = "quiz_firs")val quizFirs:String,
        @ColumnInfo(name = "quiz_second")val quizSecond:String,
        @ColumnInfo(name = "quiz_third")val quizThird:String,
        @ColumnInfo(name = "quiz_right")val quizRight:String,
        @ColumnInfo(name = "quiz_answer_check")val quizAnswerCheck:Int,
        @ColumnInfo(name = "quiz_answer_commentary")val quizCommentary:String,
        @ColumnInfo(name = "time_stamp")val timeStamp: LocalDateTime,
        @ColumnInfo(name = "relation_workbook")val relationWorkBook:Int
):Parcelable


/**
 * ■履歴を保存するためのエンティティ
 * @param historyNo        識別番号
 * @param historyRate      正解
 * @param historyDate      日付
 * @param relationQuiz     解答と紐づくナンバー
 */
@Parcelize
@Entity(tableName = "question_history")
data class QuestionHistoryEntity(
        @PrimaryKey(autoGenerate = true)val historyNo:Int,
        @ColumnInfo(name = "history_rate")val historyRate:Int,
        @ColumnInfo(name = "history_date")val historyDate:LocalDate,
        @ColumnInfo(name = "relation_quiz")val relationQuiz:Int
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
        )val workBookList:List<QuestionWorkBookEntity>,
):Parcelable




@Parcelize
data class WorkBookWithAll(
        @Embedded
        val workBookEntity:QuestionWorkBookEntity,
        @Relation(
                parentColumn = "workBookNo",
                entityColumn = "relation_workbook" )
        val textList:List<QuestionQuizEntity>,
        @Relation(
                parentColumn = "workBookNo",
                entityColumn = "relation_workbook" )
        val accuracyList:List<QuestionAccuracyEntity>,
        @Relation(
                parentColumn = "workBookNo",
                entityColumn = "relation_workbook"
        )val quizList:List<QuestionQuizEntity>
        ):Parcelable


/**
 * ■単一のクイズデータに紐づくヒストリーリストを取得
 * @param quizEntity  親となるエンティティを指定（クイズエンティティ）
 * @param historyList 子となるエンティティすべてをリストに格納。
 */
@Parcelize
data class QuizWithHistoryList(
        @Embedded
        val quizEntity: QuestionQuizEntity,
        @Relation(
                parentColumn = "quizNo",
                entityColumn = "relation_quiz"
        )val historyList: List<QuestionHistoryEntity>
):Parcelable


///////////////////////////////////////////////////////////////////////////////
//削除予定となるエンティティ//////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
/**
 * ■問題のテキストとなるデータを表示
 * ・削除予定
 * @param textNo            識別番号
 * @param textStatement     問題文
 * @param textFlag          0..ホルダー、ゲーム表示可 1..ゲーム一覧非表示  2..削除候補
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
 * ワークブック（問題集）の識別番号に紐づいた、テキスト（問題）をすべて取得する。
 */
@Parcelize
data class WorkBookWithTextAndAccuracy(
        @Embedded
        val workBookEntity:QuestionWorkBookEntity,
        @Relation(
                parentColumn = "workBookNo",
                entityColumn = "relation_workbook" )
        val textList:List<QuestionQuizEntity>,
        @Relation(
                parentColumn = "workBookNo",
                entityColumn = "relation_workbook" )
        val accuracyList:List<QuestionAccuracyEntity>
):Parcelable


/**
 *問題となるテキストの識別番号に紐づいた、回答欄すべてを取得する。
 */
@Parcelize
data class TextWithAnswer(
        @Embedded
        val textEntity: QuestionTextEntity,
        @Relation(
                parentColumn = "textNo",
                entityColumn = "relation_text"
        )val answer:QuestionAnswerEntity
):Parcelable

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////