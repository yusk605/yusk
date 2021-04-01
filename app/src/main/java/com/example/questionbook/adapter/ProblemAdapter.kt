package com.example.questionbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.room.ProblemWithAnswer
import com.example.questionbook.room.QuestionProblemEntity
import com.google.android.material.textfield.TextInputEditText

class ProblemAdapter(
    private val onClick:(QuestionProblemEntity,View)->Unit
):ListAdapter<ProblemWithAnswer,ProblemAdapter.ProblemHolder>(Diff) {

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

        val itemProblemStatementEdit   =    view.findViewById<TextInputEditText>(R.id.item_problem_statement_edit)
        val itemProblemAnswerFirst     =    view.findViewById<TextInputEditText>(R.id.item_problem_answer_first)
        val itemProblemAnswerSecond    =    view.findViewById<TextInputEditText>(R.id.item_problem_answer_second)
        val itemProblemAnswerThird     =    view.findViewById<TextInputEditText>(R.id.item_problem_answer_third)
        val itemProblemAnswerRight     =    view.findViewById<TextInputEditText>(R.id.item_problem_answer_right)
        val itemProblemPageCount       =    view.findViewById<TextView>(R.id.item_problem_page_count)

        init {
            onClick(
                getItem(layoutPosition).problemEntity,
                itemView
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemHolder =
        ProblemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_problem_layout,parent,false)
        )

    override fun onBindViewHolder(holder: ProblemHolder, position: Int) {
        val answer = getItem(position).let {
                item -> item.answerList.filter {
                    it.relationProblem == item.problemEntity.problemNo
                }.first()
            }
        holder.also {
            it.itemProblemStatementEdit.setText(getItem(position).problemEntity.problemStatement)
            it.itemProblemAnswerFirst.setText(answer.answerFirs)
            it.itemProblemAnswerSecond.setText(answer.answerSecond)
            it.itemProblemAnswerThird.setText(answer.answerThird)
            it.itemProblemAnswerRight.setText(answer.answerRight)
            it.itemProblemPageCount.text = "${currentList.indexOf(getItem(position))+1}"
       }
    }

    private fun Int.toConverterRadioButtonId() =
        when(this){
            1 -> R.id.problem_radio_button_first
            2 -> R.id.problem_radio_button_second
            3 -> R.id.problem_radio_button_third
            else -> R.id.problem_radio_button_fourth
        }

}