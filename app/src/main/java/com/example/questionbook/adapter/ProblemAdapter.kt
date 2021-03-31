package com.example.questionbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.room.ProblemWithAnswer
import com.example.questionbook.room.QuestionProblemEntity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_problem.view.*

class ProblemAdapter(private val onClick:(QuestionProblemEntity)->Unit):ListAdapter<ProblemWithAnswer,ProblemAdapter.ProblemHolder>(Diff) {

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
        val statementEditText = view.findViewById<TextInputEditText>(R.id.problem_statement_edit_text)
        val radioGroup = view.findViewById<RadioGroup>(R.id.problem_radio_group)
        //val problem_counter =
        init {
            onClick(
                getItem(layoutPosition).problemEntity
            )
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemHolder =
        ProblemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_problem,parent,false)
        )

    override fun onBindViewHolder(holder: ProblemHolder, position: Int) {
       holder.also {
           it.statementEditText.setText(
               getItem(position).problemEntity.problemStatement
           )
       }
    }
}