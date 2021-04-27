package com.example.questionbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.QuestionItem
import com.example.questionbook.R
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText

class ConcreteResultListAdapter:ListAdapter<QuestionItem,ConcreteResultListAdapter.ConcreteResultListHolder>(Diff) {

    companion object Diff:DiffUtil.ItemCallback<QuestionItem>() {
        override fun areItemsTheSame(oldItem: QuestionItem, newItem: QuestionItem): Boolean =
                oldItem == newItem

        override fun areContentsTheSame(oldItem: QuestionItem, newItem: QuestionItem): Boolean =
                oldItem == newItem
    }

    inner  class ConcreteResultListHolder(private val view:View):RecyclerView.ViewHolder(view){

        //ラベルとしてホルダークラスに必要ないかもしれないが一応　レイアウトファイル内で直打ちで "問" と指定されています。
        val itemConcreteResultListLabelTextView = view.findViewById<TextView>(R.id.item_concrete_result_list_label_text)

        val itemConcreteResultListCheck         = view.findViewById<ImageView>(R.id.item_concrete_result_list_check)
        val itemConcreteResultListCount         = view.findViewById<TextView>(R.id.item_concrete_result_list_count)
        val itemConcreteResultListStatementEdit = view.findViewById<TextInputEditText>(R.id.item_concrete_result_list_statement_edit)
        val itemConcreteResultListSelectAnswer  = view.findViewById<TextInputEditText>(R.id.item_concrete_result_list_select_answer)
        val itemConcreteResultListRightAnswer   = view.findViewById<TextInputEditText>(R.id.item_concrete_result_list_right_answer)
        val itemConcreteResultListChip          = view.findViewById<Chip>(R.id.item_concrete_result_list_chip)
        val itemConcreteResultListButton        = view.findViewById<Button>(R.id.item_concrete_result_list_show_dialog)

        init {
            itemConcreteResultListButton.setOnClickListener {
                //TODO 解説のダイヤログを表示さえる処理を記述すること。
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ConcreteResultListAdapter.ConcreteResultListHolder =
            ConcreteResultListHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_result_layout,parent,false)
            )

    override fun onBindViewHolder(holder: ConcreteResultListHolder, position: Int) {
        val item = getItem(position)
        holder.also {
            it.itemConcreteResultListCount.text = "${item.historyQuizNumber}"
            it.itemConcreteResultListStatementEdit.setText(item.entity.quizStatement)
            it.itemConcreteResultListSelectAnswer.setText(item.selectAnswer)
            it.itemConcreteResultListRightAnswer.setText(item.entity.quizRight)
            it.itemConcreteResultListChip.text = item.questionTitle
            it.itemConcreteResultListCheck.toImageConvert(item.answerCheck)
        }
    }

    /**
     * ■表示する画像を動的に変更する。
     * @param type 1..正解となる画像を表示 2..不正解となる画像を表示 0..何も表示させない
     * 引き数となるタイプを指定することで、表示する画像を動的に変更すること。
     */
    private fun ImageView.toImageConvert(type:Int){
        when(type){
            1 -> setImageResource(R.drawable.result_right)
            2 -> setImageResource(R.drawable.result_lncorrect_answer)
            else -> isVisible = false
        }
    }
}