package com.example.questionbook.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.room.CategoryWithWorkBooks
import com.example.questionbook.room.QuestionCategoryEntity

/**
 * ■項目一覧を取得する
 * @param onClick 第一引数 QuestionCategoryEntity 第二引数 View 遷移を行う際に必要な値
 */
class CategoryListAdapter(
        private val onClick:(QuestionCategoryEntity,View)->Unit
    ):ListAdapter< CategoryWithWorkBooks,CategoryListAdapter.CategoryAdapterHolder >(Diff){

    companion object Diff: DiffUtil.ItemCallback<CategoryWithWorkBooks>(){
        override fun areItemsTheSame(
            oldItem: CategoryWithWorkBooks,
            newItem: CategoryWithWorkBooks
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: CategoryWithWorkBooks,
            newItem: CategoryWithWorkBooks
        ): Boolean = oldItem == newItem
    }

    inner class CategoryAdapterHolder(view: View):RecyclerView.ViewHolder(view){

        private val _categoryTitle: TextView = view.findViewById(R.id.item_category_title)
        private val _categoryWorkBookCount:TextView = view.findViewById(R.id.item_category_workbook_count)


        init {
                itemView.setOnClickListener {
                    onClick(
                            getItem(layoutPosition).questionCategoryEntity,
                            itemView
                    )
                }
            }

        val categoryTitle
            get() = _categoryTitle

        val categoryWorkbookCount
            get() = _categoryWorkBookCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterHolder {
        return CategoryAdapterHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_category_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: CategoryAdapterHolder, position: Int) {
        val item = getItem(position)
        holder.also {
            h->
            h.categoryTitle.text = item.questionCategoryEntity.categoryTitle
            h.categoryWorkbookCount.text = "×${item.workBookList.filter { it.workBookFlag == 0 ||it.workBookFlag == 1 }.size}"
        }
    }
}