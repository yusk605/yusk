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
import com.example.questionbook.room.QuestionQuizEntity
import com.google.android.material.textfield.TextInputEditText

class QuizAdapter:ListAdapter<QuestionQuizEntity,QuizAdapter.QuizHolder>(Diff) {

    companion object Diff: DiffUtil.ItemCallback<QuestionQuizEntity>(){
        override fun areItemsTheSame(
                oldItem: QuestionQuizEntity,
                newItem: QuestionQuizEntity
        ): Boolean = oldItem == newItem
        override fun areContentsTheSame(
                oldItem: QuestionQuizEntity,
                newItem: QuestionQuizEntity
        ): Boolean = oldItem == newItem
    }


    inner class QuizHolder(view: View):RecyclerView.ViewHolder(view){
        val itemTextStatementEdit   =    view.findViewById<TextInputEditText>(R.id.game_start_statement_edit)
        val itemTextAnswerFirst     =    view.findViewById<TextInputEditText>(R.id.list_text_answer_first)
        val itemTextAnswerSecond    =    view.findViewById<TextInputEditText>(R.id.dialog_text_answer_second)
        val itemTextAnswerThird     =    view.findViewById<TextInputEditText>(R.id.list_text_answer_third)
        val itemTextAnswerRight     =    view.findViewById<TextInputEditText>(R.id.list_text_answer_right)
        val itemTextPageCount       =    view.findViewById<TextView>(R.id.item_text_page_count)
        val itemIcButton            =    view.findViewById<ImageView>(R.id.item_text_amendment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizHolder =
        QuizHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_quiz_text_layout,parent,false)
        )

    override fun onBindViewHolder(holder: QuizHolder, position: Int) {
        val entity = getItem(position)
         holder.also {
            it.itemTextStatementEdit.setText(getItem(position).quizStatement)
            it.itemTextAnswerFirst.setText(entity.quizFirs)
            it.itemTextAnswerSecond.setText(entity.quizSecond)
            it.itemTextAnswerThird.setText(entity.quizThird)
            it.itemTextAnswerRight.setText(entity.quizRight)
            it.itemTextPageCount.text = "${currentList.indexOf(getItem(position))+1}"
        }
    }
}