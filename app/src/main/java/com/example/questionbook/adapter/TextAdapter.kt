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
import com.example.questionbook.room.TextWithAnswer
import com.example.questionbook.room.TextWithAnswerAndHistory
import com.google.android.material.textfield.TextInputEditText

class TextAdapter(
    //private val onClick:(View, QuestionTextEntity)->Unit
):ListAdapter<TextWithAnswer,TextAdapter.TextmHolder>(Diff) {

    companion object Diff:DiffUtil.ItemCallback<TextWithAnswer>(){
        override fun areItemsTheSame(
                oldItem: TextWithAnswer,
                newItem: TextWithAnswer
        ): Boolean = oldItem == newItem
        override fun areContentsTheSame(
                oldItem: TextWithAnswer,
                newItem: TextWithAnswer
        ): Boolean = oldItem == newItem
    }

    inner class TextmHolder(view:View):RecyclerView.ViewHolder(view){

        val itemTextStatementEdit   =    view.findViewById<TextInputEditText>(R.id.form_text_statement_edit)
        val itemTextAnswerFirst     =    view.findViewById<TextInputEditText>(R.id.form_text_answer_first)
        val itemTextAnswerSecond    =    view.findViewById<TextInputEditText>(R.id.form_text_answer_second)
        val itemTextAnswerThird     =    view.findViewById<TextInputEditText>(R.id.form_problem_text_third)
        val itemTextAnswerRight     =    view.findViewById<TextInputEditText>(R.id.form_text_answer_right)
        val itemTextPageCount       =    view.findViewById<TextView>(R.id.item_text_page_count)
        val itemTextAmendment       =    view.findViewById<ImageView>(R.id.item_text_amendment)

       /* init {
            itemTextAmendment.setOnClickListener {
                onClick(it,getItem(layoutPosition).problemEntity)
            }
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextmHolder =
        TextmHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_text_layout,parent,false)
        )

    override fun onBindViewHolder(holder: TextmHolder, position: Int) {
        val answer = getItem(position).answer
        holder.also {
            it.itemTextStatementEdit.setText(getItem(position).textEntity.textStatement)
            it.itemTextAnswerFirst.setText(answer.answerFirs)
            it.itemTextAnswerSecond.setText(answer.answerSecond)
            it.itemTextAnswerThird.setText(answer.answerThird)
            it.itemTextAnswerRight.setText(answer.answerRight)
            it.itemTextPageCount.text = "${currentList.indexOf(getItem(position))+1}"
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