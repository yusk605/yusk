package com.example.questionbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.room.AccuracyWithHistory
import com.google.android.material.chip.Chip

class AccuracyRecycleAdapter(
        private val onClick:(AccuracyWithHistory,View) -> Unit,
        private val problemTitle:String,
        private val categoryTitle:String
) :ListAdapter<AccuracyWithHistory,AccuracyRecycleAdapter.AccuracyRecycleHolder>(Diff) {

    companion object Diff:DiffUtil.ItemCallback<AccuracyWithHistory>(){
        override fun areItemsTheSame(oldItem: AccuracyWithHistory, newItem: AccuracyWithHistory): Boolean =
                oldItem == newItem
        override fun areContentsTheSame(oldItem: AccuracyWithHistory, newItem: AccuracyWithHistory): Boolean =
                oldItem == newItem
    }

    inner class AccuracyRecycleHolder(view: View):RecyclerView.ViewHolder(view){
        val accuracyItemTitle   = view.findViewById<TextView>(R.id.item_accuracy_title)
        val accuracyItemCount   = view.findViewById<TextView>(R.id.item_accuracy_counter_text)
        val accuracyProgressBar = view.findViewById<ProgressBar>(R.id.accuracy_progress_bar)
        val accuracyItem        = view.findViewById<TextView>(R.id.item_accuracy)
        val accuracyChip        = view.findViewById<Chip>(R.id.item_category_chip)

        init {
            view.setOnClickListener {
                onClick(getItem(layoutPosition),it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccuracyRecycleHolder {
        return AccuracyRecycleHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_accuracy_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: AccuracyRecycleHolder, position: Int) {

        //正解した値のみを取得する
        val rate:Int = getItem(position)
                .historyList
                .filter { li->li.historyRate == 1 }
                .map    { it.historyRate }
                .sum()

        //問題となる解答率を表示
        val accuracy:Int = (
                rate / getItem(position)
                    .historyList
                    .filter { it.relationAccuracy == getItem(position).accuracyEntity.accuracyNo }
                    .toList()
                    .size
                ) * 100

        holder.let {
            it.accuracyItemTitle.text = problemTitle
            it.accuracyChip.text = categoryTitle
            it.accuracyItemCount.text = getItem(position).historyList.filter{ f->
                    f.relationText == getItem(position).accuracyEntity.accuracyNo
                }.size.toString()
            it.accuracyItem.text = "${accuracy}%"
            it.accuracyProgressBar.progress = accuracy
        }
    }

}