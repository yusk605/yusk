package com.example.questionbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.room.WorkBookWithTextAndAccuracy
import com.google.android.material.chip.Chip

/**
 * ■問題集一覧を表示するためのデータを中間的な役割を果たすクラス
 * @param categoryTitle 表示するチップビューの項目のタイトルを表示するため
 * @param onClick   問題集に関連するデータとビューを引数に取る関数型オブジェクト。
 * ・なお、onClickを実装した理由としては項目をタップした時に、
 * 　その項目に紐づいたリスト一覧を表示させるための必要な処理として。
 */
class WorkBookAdapter(
    private val categoryTitle:String,
    private val onClick:(View, WorkBookWithTextAndAccuracy) -> Unit
):ListAdapter<WorkBookWithTextAndAccuracy,WorkBookAdapter.WorkBookHolder>(Diff) {

    companion object Diff: DiffUtil.ItemCallback<WorkBookWithTextAndAccuracy>() {
        override fun areItemsTheSame(
                oldItem: WorkBookWithTextAndAccuracy,
                newItem: WorkBookWithTextAndAccuracy
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
                oldItem: WorkBookWithTextAndAccuracy,
                newItem: WorkBookWithTextAndAccuracy
        ): Boolean = oldItem == newItem

    }

    inner class WorkBookHolder(private val view: View):RecyclerView.ViewHolder(view){
       val  itemWorkBookTitle = view.findViewById<TextView>(R.id.item_workbook_title)
       val  itemWorkBookProblemCount = view.findViewById<TextView>(R.id.item_workbook_problem_count)
       val  itemWorkBookAccuracy = view.findViewById<TextView>(R.id.item_workbook_accuracy)
       val  itemWorkBookChip    = view.findViewById<Chip>(R.id.game_start_chip)
        init {
            view.setOnClickListener {
                onClick(
                    //遷移する際に必要なオブジェクトを渡すため。
                        view, getItem(adapterPosition)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkBookHolder =
        WorkBookHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_workbook_layout,parent,false)
        )

    override fun onBindViewHolder(holder: WorkBookHolder, position: Int) {
       val average = getItem(position).accuracyList.map {a-> a.accuracyRate }.toList().average()
       holder.also {
           it.itemWorkBookTitle.text = getItem(position).workBookEntity.workBookTitle
           it.itemWorkBookProblemCount.text = "${getItem(position).textList.size}"
           it.itemWorkBookAccuracy.text = "$average%"
           it.itemWorkBookChip.text = categoryTitle
       }
    }
 }