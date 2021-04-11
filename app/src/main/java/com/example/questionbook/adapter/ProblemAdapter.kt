package com.example.questionbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.room.TextWithAnswerAndHistory
import com.example.questionbook.room.QuestionTextEntity
import com.google.android.material.textfield.TextInputEditText

class ProblemAdapter(
    private val onClick:(View, QuestionTextEntity)->Unit
):ListAdapter<TextWithAnswerAndHistory,ProblemAdapter.ProblemHolder>(Diff) {

    companion object Diff:DiffUtil.ItemCallback<TextWithAnswerAndHistory>(){
        override fun areItemsTheSame(
                oldItem: TextWithAnswerAndHistory,
                newItem: TextWithAnswerAndHistory
        ): Boolean = oldItem == newItem
        override fun areContentsTheSame(
                oldItem: TextWithAnswerAndHistory,
                newItem: TextWithAnswerAndHistory
        ): Boolean = oldItem == newItem
    }

    inner class ProblemHolder(view:View):RecyclerView.ViewHolder(view){

        val itemProblemStatementEdit   =    view.findViewById<TextInputEditText>(R.id.form_problem_statement_edit)
        val itemProblemAnswerFirst     =    view.findViewById<TextInputEditText>(R.id.form_problem_answer_first)
        val itemProblemAnswerSecond    =    view.findViewById<TextInputEditText>(R.id.form_problem_answer_second)
        val itemProblemAnswerThird     =    view.findViewById<TextInputEditText>(R.id.form_problem_answer_third)
        val itemProblemAnswerRight     =    view.findViewById<TextInputEditText>(R.id.form_problem_answer_right)
        val itemProblemPageCount       =    view.findViewById<TextView>(R.id.item_problem_page_count)
        val itemProblemAmendment       =    view.findViewById<ImageView>(R.id.item_problem_amendment)

        init {
            itemProblemAmendment.setOnClickListener {
                onClick(it,getItem(layoutPosition).problemEntity)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemHolder =
        ProblemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_text_layout,parent,false)
        )

    override fun onBindViewHolder(holder: ProblemHolder, position: Int) {
        val answer = getItem(position).answer
        holder.also {
            it.itemProblemStatementEdit.setText(getItem(position).problemEntity.textStatement)
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