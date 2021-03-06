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
import com.example.questionbook.R
import com.example.questionbook.room.LeafWithHistory
import com.example.questionbook.room.QuestionHistoryEntity
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText

class ResultDetailsListAdapter(
        private val title:String
        ):ListAdapter<QuestionHistoryEntity,ResultDetailsListAdapter.ConcreteResultListHolder>(Diff) {

    companion object Diff:DiffUtil.ItemCallback<QuestionHistoryEntity>() {
        override fun areItemsTheSame(oldItem:QuestionHistoryEntity, newItem:QuestionHistoryEntity): Boolean =
                oldItem == newItem

        override fun areContentsTheSame(oldItem:QuestionHistoryEntity, newItem:QuestionHistoryEntity): Boolean =
                oldItem == newItem
    }

    inner  class ConcreteResultListHolder(private val view:View):RecyclerView.ViewHolder(view){

        //ラベルとしてホルダークラスに必要ないかもしれないが一応　レイアウトファイル内で直打ちで "問" と指定されています。
        val itemConcreteResultListLabelTextView = view.findViewById<TextView>(R.id.item_concrete_result_list_label_text)!!

        val itemConcreteResultListCheck         = view.findViewById<ImageView>(R.id.item_concrete_result_list_check)!!
        val itemConcreteResultListCount         = view.findViewById<TextView>(R.id.item_concrete_result_list_count)!!
        val itemConcreteResultListStatementEdit = view.findViewById<TextInputEditText>(R.id.item_concrete_result_list_statement_edit)!!
        val itemConcreteResultListSelectAnswer  = view.findViewById<TextInputEditText>(R.id.item_concrete_result_list_select_answer)!!
        val itemConcreteResultListRightAnswer   = view.findViewById<TextInputEditText>(R.id.item_concrete_result_list_right_answer)!!
        val itemConcreteResultListChip          = view.findViewById<Chip>(R.id.item_concrete_result_list_chip)!!
        val itemConcreteResultListFirst         = view.findViewById<TextInputEditText>(R.id.item_concrete_result_list_first)!!
        val itemConcreteResultListSecond        = view.findViewById<TextInputEditText>(R.id.item_concrete_result_list_second)!!
        val itemConcreteResultListThird        = view.findViewById<TextInputEditText>(R.id.item_concrete_result_list_third)!!

        private val itemConcreteResultListButton        = view.findViewById<Button>(R.id.item_concrete_result_list_show_dialog)!!

        init {
            itemConcreteResultListButton.setOnClickListener {
                //TODO 解説のダイヤログを表示さえる処理を記述すること。
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ResultDetailsListAdapter.ConcreteResultListHolder =
            ConcreteResultListHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.layout_result_item,parent,false)
            )

    override fun onBindViewHolder(holder: ConcreteResultListHolder, position: Int) {
        val item = getItem(position)
        holder.also {
            it.itemConcreteResultListCount.text = "${item.historyLeafNumber}"
            it.itemConcreteResultListStatementEdit.setText(item.historyLeafStatement)
            it.itemConcreteResultListSelectAnswer.setText(item.historyLeafSelectAnswer)
            it.itemConcreteResultListRightAnswer.setText(item.historyLeafRate)
            it.itemConcreteResultListChip.text= title
            it.itemConcreteResultListCheck.toImageConvert(item.historyCheck)
            it.itemConcreteResultListFirst.setText(item.historyLeafFirst)
            it.itemConcreteResultListSecond.setText(item.historyLeafSecond)
            it.itemConcreteResultListThird.setText(item.historyLeafThird)
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