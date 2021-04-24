package com.example.questionbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.room.QuestionAccuracyEntity
import com.example.questionbook.room.WorkBookWithAll

class ConcreteResultAdapter(
        private val title:String,
        private val onClick:(WorkBookWithAll,View) -> Unit
) :ListAdapter<WorkBookWithAll,ConcreteResultAdapter.ConcreteResultHolder>(Diff) {

    companion object Diff: DiffUtil.ItemCallback<WorkBookWithAll>(){
        override fun areItemsTheSame(oldItem:WorkBookWithAll, newItem:WorkBookWithAll): Boolean =
                oldItem == newItem

        override fun areContentsTheSame(oldItem:WorkBookWithAll, newItem: WorkBookWithAll): Boolean =
                oldItem == newItem
    }

    inner class ConcreteResultHolder(view: View):RecyclerView.ViewHolder(view){
            val title       = view.findViewById<TextView>(R.id.item_accuracy_title)
            val progress    = view.findViewById<ProgressBar>(R.id.item_accuracy_progress_bar)
            val accuracy    = view.findViewById<TextView>(R.id.item_accuracy_text_view)
            val quizCount   = view.findViewById<TextView>(R.id.item_accuracy_counter_text)
        init {
            onClick(
                    getItem(layoutPosition),
                    view
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcreteResultHolder =
            ConcreteResultHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_accuracy_layout,parent,false)
            )

    override fun onBindViewHolder(holder: ConcreteResultHolder, position: Int) {
       var  accuracyList = getItem(position).accuracyList
               .filter { it.relationWorkBook == getItem(position).workBookEntity.workBookNo }
               .toList()


       holder.also {
           it.title.text = getItem(position).workBookEntity.workBookTitle
           it.accuracy.text = "${accuracyList}%"
           it.quizCount
       }
    }
}