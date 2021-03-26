package com.example.questionbook.adapter


import android.media.browse.MediaBrowser
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.room.CategoryWithWorkBooks
import com.example.questionbook.room.QuestionCategoryEntity
import org.w3c.dom.Text

class CategoryAdapter(
        private val onClick:(QuestionCategoryEntity,View)->Unit
    ):ListAdapter< CategoryWithWorkBooks,CategoryAdapter.CategoryAdapterHolder >(Diff){

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
        private val _categoryWorkBookCount:TextView = view.findViewById(R.id.category_workbook_count)

        init {
                itemView.setOnClickListener {
                    onClick(getItem(layoutPosition).questionCategoryEntity, itemView)
                }
            }

        val categoryTitle
            get() = _categoryTitle

        val categoryWorkbookCount
            get() = _categoryWorkBookCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterHolder {
        return CategoryAdapterHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: CategoryAdapterHolder, position: Int) {
        holder.also {
            it.categoryTitle.text = getItem(position).questionCategoryEntity.categoryTitle
            it.categoryWorkbookCount.text = getItem(position).workBookList.size.toString()
        }
    }
}