package com.example.questionbook.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.room.QuestionLeafEntity
import com.google.android.material.textfield.TextInputEditText

class LeafListAdapter(
        private val onClickDelete:(QuestionLeafEntity)->Unit,
        private val onClickUpdate:(QuestionLeafEntity)->Unit,
    ) :ListAdapter<QuestionLeafEntity,LeafListAdapter.QuizHolder>(Diff) {

    companion object Diff: DiffUtil.ItemCallback<QuestionLeafEntity>(){
        override fun areItemsTheSame(
                oldItem: QuestionLeafEntity,
                newItem: QuestionLeafEntity
        ): Boolean = oldItem == newItem
        override fun areContentsTheSame(
                oldItem: QuestionLeafEntity,
                newItem: QuestionLeafEntity
        ): Boolean = oldItem == newItem
    }

    inner class QuizHolder(view: View):RecyclerView.ViewHolder(view){
        val itemTextStatementEdit   = view.findViewById<TextInputEditText>(R.id.item_leaf_statement_edit)!!
        val itemTextAnswerFirst     = view.findViewById<TextInputEditText>(R.id.item_leaf_select_answer_fist)!!
        val itemTextAnswerSecond    = view.findViewById<TextInputEditText>(R.id.item_leaf_select_answer_second)!!
        val itemTextAnswerThird     = view.findViewById<TextInputEditText>(R.id.item_leaf_select_answer_third)!!
        val itemTextAnswerRight     = view.findViewById<TextInputEditText>(R.id.item_leaf_select_answer_right)!!
        val itemTextPageCount       = view.findViewById<TextView>(R.id.item_leaf_page_count)!!
        val itemDeleteButton        = view.findViewById<Button>(R.id.item_leaf_delete_button)!!
        private val itemIcButton            = view.findViewById<ImageView>(R.id.item_leaf_amendment)!!
        init {
            itemIcButton.setOnClickListener { onClickUpdate(getItem(layoutPosition)) }
            itemDeleteButton.setOnClickListener {
                Log.d("tag","sample_test")
                onClickDelete(getItem(layoutPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizHolder =
        QuizHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_leaf_layot,parent,false)
        )

    override fun onBindViewHolder(holder: QuizHolder, position: Int) {
        val entity = getItem(position)
         holder.also {
             h->
             h.itemTextStatementEdit.setText(getItem(position).leafStatement)
             h.itemTextAnswerFirst.setText(entity.leafFirs)
             h.itemTextAnswerSecond.setText(entity.leafSecond)
             h.itemTextAnswerThird.setText(entity.leafThird)
             h.itemTextAnswerRight.setText(entity.leafRight)
             h.itemTextPageCount.text = "${position+1}"
        }
    }
}