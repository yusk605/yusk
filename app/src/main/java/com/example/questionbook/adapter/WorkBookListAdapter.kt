package com.example.questionbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.MainActivity
import com.example.questionbook.PopupMenuSelectItems
import com.example.questionbook.R
import com.example.questionbook.room.WorkBookWithAll

import com.google.android.material.snackbar.Snackbar

/**
 * ■問題集一覧を表示するためのデータを中間的な役割を果たすクラス
 * @param categoryTitle 表示するチップビューの項目のタイトルを表示するため
 * @param onClick   問題集に関連するデータとビューを引数に取る関数型オブジェクト。
 * ・なお、onClickを実装した理由としては項目をタップした時に、
 * 　その項目に紐づいたリスト一覧を表示させるための必要な処理として。
 */
class WorkBookListAdapter(
        private val categoryTitle:String,
        private val check:Int,
        private val onClick:(View, WorkBookWithAll) -> Unit
):ListAdapter<WorkBookWithAll,WorkBookListAdapter.WorkBookHolder>(Diff) {

    private val onClickSafety:(View) -> Unit =
            { v-> Snackbar.make(v,"クイズがありません",Snackbar.LENGTH_SHORT).show() }

    companion object Diff: DiffUtil.ItemCallback<WorkBookWithAll>() {
        override fun areItemsTheSame(
                oldItem: WorkBookWithAll, newItem: WorkBookWithAll
        ): Boolean = oldItem == newItem
        override fun areContentsTheSame(
                oldItem: WorkBookWithAll, newItem: WorkBookWithAll
        ): Boolean = oldItem == newItem
    }

    inner class WorkBookHolder(private val view: View):RecyclerView.ViewHolder(view){
       val  itemWorkBookTitle = view.findViewById<TextView>(R.id.item_workbook_title)
       val  itemWorkBookProblemCount = view.findViewById<TextView>(R.id.item_workbook_problem_count)
       val  itemWorkBookAccuracy = view.findViewById<TextView>(R.id.item_workbook_accuracy_rate)
     // val  itemWorkBookChip    = view.findViewById<Chip>(R.id.item_workbook_chip)
        init {
            view.setOnClickListener {
                onClickSafety(
                        layoutPosition,
                        view,
                        getItem(adapterPosition)
                )
            }
        }
    }

    /**
     * ■問題集にクイズとなるデータが存在しない場合は遷移しない
     * @param position 項目をタップした添え字番号
     */
    fun onClickSafety(position: Int,view: View,data:WorkBookWithAll){
        if ( getItem(position).quizList.isEmpty() &&
                check == MainActivity.actionGameValue
            ){
                onClickSafety(view)
        }else{
            onClick(view, data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkBookHolder =
        WorkBookHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_workbook_item,parent,false)
        )

    override fun onBindViewHolder(holder: WorkBookHolder, position: Int) {
       val item = getItem(position)
       val max = item.accuracyList.map { a-> a.accuracyRate }.maxOrNull().toString().nullConverter()
       holder.also {
           it.itemWorkBookTitle.text = getItem(position).workBookEntity.workBookTitle
           it.itemWorkBookProblemCount.text = "×${item.leafList.size}"
           it.itemWorkBookAccuracy.text = max
         //  it.itemWorkBookChip.text = categoryTitle
       }
    }
    private fun String.nullConverter() = if (equals("null"))"なし" else "${this}%"

 }