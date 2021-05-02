package com.example.questionbook.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.room.AccuracyWithHistory
import com.example.questionbook.room.QuestionAccuracyEntity
import kotlinx.android.synthetic.main.layout_history_item.view.*
import java.time.format.DateTimeFormatter

//履歴を詳細をタップした時に
class HistoryListAdapter(
    private val onClick:(View,Int)->Unit
): ListAdapter<QuestionAccuracyEntity,HistoryListAdapter.HistoryListAdapterHolder>(Diff) {

    companion object Diff:DiffUtil.ItemCallback<QuestionAccuracyEntity>(){
        override fun areItemsTheSame(
            oldItem: QuestionAccuracyEntity,
            newItem: QuestionAccuracyEntity
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: QuestionAccuracyEntity,
            newItem: QuestionAccuracyEntity
        ): Boolean = oldItem == newItem
    }

    inner class HistoryListAdapterHolder(view:View):RecyclerView.ViewHolder(view){
        val itemHistoryDateText = view.findViewById<TextView>(R.id.item_history_date_text)!!
        val itemHistoryDateTimeText = view.findViewById<TextView>(R.id.item_history_datetime_text)!!
        val itemHistoryProgressBer = view.findViewById<ProgressBar>(R.id.item_history_progressber)!!
        val itemHistoryAccuracyRateText = view.findViewById<TextView>(R.id.item_history_accuracy_rate_text)!!
        init {
            itemView.setOnClickListener {
                onClick( itemView, getItem(layoutPosition).accuracyNo )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryListAdapterHolder =
        HistoryListAdapterHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_history_item,parent,false)
        )

    override fun onBindViewHolder(holder: HistoryListAdapterHolder, position: Int) {
        val item = getItem(position)
        val date = item.timeStamp
        holder.also { h->
            h.itemHistoryDateText.text = date.format(
                DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            h.itemHistoryDateTimeText.text = date.format(
                DateTimeFormatter.ofPattern("HH:mm"))
            h.itemHistoryAccuracyRateText.text = "${item.accuracyRate.toInt()}%"

        }
    }
}