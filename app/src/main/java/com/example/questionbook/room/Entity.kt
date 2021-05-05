package com.example.questionbook.room

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

/**
 * ■カテゴリーの分類分けをするエンティティ
 *  @param categoryNo　   識別番号
 *  @param categoryTitle  カテゴリーでのタイトル。
 *  @param categoryFlag　 0..ホルダー、ゲーム表示可 1..ゲーム一覧非表示  2..削除候補
 *  @param timeStamp      更新日時　
 */
@Parcelize
@Entity(tableName = "question_category")
data class QuestionCategoryEntity(
        @PrimaryKey(autoGenerate = true)val categoryNo:Int,
        @ColumnInfo(name = "category_title")var categoryTitle:String,
        @ColumnInfo(name="category_flag")var categoryFlag:Int,
        @ColumnInfo(name="time_stamp")var timeStamp:LocalDateTime
):Parcelable

/**
 * ■問題集の分類分けをするエンティティ
 * @param workBookNo 識別番号
 * @param workBookTitle 問題集のタイトル
 * @param timeStamp timestamp 更新日：更新時間
 * @param workBookFlag 0..ホルダー、ゲーム表示可 1..ゲーム一覧非表示 2..削除候補
 * @param relationCategory question_category categoryNo 紐づけとなるナンバー
 */
@Parcelize
@Entity(tableName = "question_workbook")
data class QuestionWorkBookEntity(
        @PrimaryKey(autoGenerate = true)val workBookNo:Int,
        @ColumnInfo(name = "workbook_title")var workBookTitle:String,
        @ColumnInfo(name = "workbook_flag")var workBookFlag:Int,
        @ColumnInfo(name = "time_stamp")var timeStamp:LocalDateTime,
        @ColumnInfo(name = "relation_category")val relationCategory:Int
):Parcelable

/**
 * ■回答率を保持するエンティティ
 * @param accuracyNo        識別番号
 * @param accuracyRate      正解率
 * @param accuracyFlag      0..デフォルト　2..削除予定
 * @param timeStamp         日付
 * @param relationWorkBook  question_workbook workBookNo 紐づけとなるナンバー
 */
@Parcelize
@Entity(tableName = "question_accuracy")
data class QuestionAccuracyEntity(
        @PrimaryKey(autoGenerate = true)val accuracyNo:Int,
        @ColumnInfo(name = "accuracy_rate")var accuracyRate:Float,
        @ColumnInfo(name = "accuracy_flag")var accuracyFlag:Int,
        @ColumnInfo(name = "time_stamp")var timeStamp:LocalDateTime,
        @ColumnInfo(name = "relation_workbook")var relationWorkBook: Int
):Parcelable

/**
 * ■解答案を保存するためのエンティティ
 * @param leafNo                識別番号
 * @param leafStatement         問題文
 * @param leafFirs              解答案その1
 * @param leafSecond            解答案その2
 * @param leafThird             解答案その3
 * @param leafRight             解答案
 * @param leafAnswerCheck       解答の値
 * @param leafCommentary        解答案
 * @param timeStamp             更新時間
 * @param relationWorkBook      問題集に紐づける番号
 */
@Parcelize
@Entity(tableName = "question_leaf")
data class QuestionLeafEntity(
        @PrimaryKey(autoGenerate = true)val leafNo:Int,
        @ColumnInfo(name = "leaf_statement")var leafStatement:String,
        @ColumnInfo(name = "leaf_firs")var leafFirs:String,
        @ColumnInfo(name = "leaf_second")var leafSecond:String,
        @ColumnInfo(name = "leaf_third")var leafThird:String,
        @ColumnInfo(name = "leaf_right")var leafRight:String,
        @ColumnInfo(name = "leaf_answer_check")var leafAnswerCheck:Int,
        @ColumnInfo(name = "leaf_answer_commentary")var leafCommentary:String,
        @ColumnInfo(name = "time_stamp")var timeStamp: LocalDateTime,
        @ColumnInfo(name = "relation_workbook")var relationWorkBook:Int
):Parcelable


/**
 * ■履歴を保存するためのエンティティ
 * @param historyNo     識別番号
 * @param historyCheck  正解
 * @param timeStamp     日付
 * @param relationLeaf  解答と紐づくナンバー
 * @param historyLeafNumber 出題番号履歴
 * @param historyLeafSelectAnswer 選択解答履歴
 * @param historyLeafRate 正解案履歴
 * @param historyLeafFirst 選択案履歴1
 * @param historyLeafSecond 選択案履歴2
 * @param historyLeafThird 選択案履歴3
 * @param historyLeafStatement 問題文履歴
 * @param relationAccuracy 外部キー question_accuracy -> accuracyNo
 * @param relationLeaf  外部キー question_leaf -> leafNo
 */
@Parcelize
@Entity(tableName = "question_history")
data class QuestionHistoryEntity(
        @PrimaryKey(autoGenerate = true)val historyNo:Int,
        @ColumnInfo(name = "history_check")val historyCheck:Int,
        @ColumnInfo(name = "history_leaf_number")val historyLeafNumber:Int,
        @ColumnInfo(name = "history_leaf_select_answer")val historyLeafSelectAnswer:String,
        @ColumnInfo(name = "history_leaf_right")val historyLeafRate:String,
        @ColumnInfo(name = "history_leaf_first")val historyLeafFirst:String,
        @ColumnInfo(name = "history_leaf_second")val historyLeafSecond:String,
        @ColumnInfo(name = "history_leaf_third")val historyLeafThird:String,
        @ColumnInfo(name = "history_leaf_statement")val historyLeafStatement:String,
        @ColumnInfo(name = "time_stamp")val timeStamp:LocalDateTime,
        @ColumnInfo(name = "relation_accuracy")val relationAccuracy:Int,
        @ColumnInfo(name ="relation_leaf")val relationLeaf:Int
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
        val leafList:List<QuestionLeafEntity>,
        @Relation(
                parentColumn = "workBookNo",
                entityColumn = "relation_workbook" )
        val accuracyList:List<QuestionAccuracyEntity>,
        @Relation(
                parentColumn = "workBookNo",
                entityColumn = "relation_workbook"
        )val quizList:List<QuestionLeafEntity>
        ):Parcelable


/**
 * ■回答率のデータに基づく単一のレコードに対して、紐づけとなるデータ。
 * @param accuracyEntity  親となるエンティティを指定（クイズエンティティ）
 * @param historyList 子となるエンティティすべてをリストに格納。
 */
@Parcelize
data class AccuracyWithHistory(
        @Embedded
        val accuracyEntity: QuestionAccuracyEntity,
        @Relation(
                parentColumn = "accuracyNo",
                entityColumn = "relation_accuracy"
        )val historyList:QuestionHistoryEntity
):Parcelable


/**
 * ■クイズを行った履歴を表示させるためのデータクラス
 * @param leafEntity    クイズを表示させるためのテーブル
 * @param historyEntity 履歴を表示させるためのテーブル
 * 上記を紐づけた理由としては、同じ日にクイズを行った場合
 * クイズとなる問題の正誤履歴を表示させるためのオブジェクト
 */
@Parcelize
data class LeafWithHistory(
        @Embedded
        val leafEntity: QuestionLeafEntity,
        @Relation(
                parentColumn = "leafNo",
                entityColumn = "relation_leaf"
        )val historyEntity: QuestionHistoryEntity
):Parcelable

