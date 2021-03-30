package com.example.questionbook.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.room.ProblemWithAnswer

class ProblemAdapter:ListAdapter<ProblemWithAnswer,ProblemAdapter.ProblemHolder>(Diff) {

    companion object Diff:DiffUtil.ItemCallback<ProblemWithAnswer>(){
        override fun areItemsTheSame(
            oldItem: ProblemWithAnswer,
            newItem: ProblemWithAnswer
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ProblemWithAnswer,
            newItem: ProblemWithAnswer
        ): Boolean = oldItem == newItem

    }
    inner class ProblemHolder(view:View):RecyclerView.ViewHolder(view){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ProblemHolder, position: Int) {
        TODO("Not yet implemented")
    }
}