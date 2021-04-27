package com.example.questionbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.QuestionItem
import com.example.questionbook.R

class ConcreteResultList:ListAdapter<QuestionItem,ConcreteResultList.ConcreteResultListHolder>(Diff) {

    companion object Diff:DiffUtil.ItemCallback<QuestionItem>() {
        override fun areItemsTheSame(oldItem: QuestionItem, newItem: QuestionItem): Boolean =
                oldItem == newItem

        override fun areContentsTheSame(oldItem: QuestionItem, newItem: QuestionItem): Boolean =
                oldItem == newItem
    }

    inner  class ConcreteResultListHolder(private val view:View):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ConcreteResultList.ConcreteResultListHolder =
            ConcreteResultListHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_result_layout,parent,false)
            )

    override fun onBindViewHolder(holder: ConcreteResultListHolder, position: Int) {
        holder.also {

        }
    }
}