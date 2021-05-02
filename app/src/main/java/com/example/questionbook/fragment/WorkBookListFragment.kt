package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.questionbook.R
import com.example.questionbook.adapter.WorkBookListAdapter
import com.example.questionbook.dialog.WorkBookDialog
import com.example.questionbook.dialog.WorkBookDialogFactory
import com.example.questionbook.room.QuestionCategoryEntity
import com.example.questionbook.room.QuestionWorkBookEntity
import com.example.questionbook.actionWorkBook
import com.example.questionbook.isHolder
import com.example.questionbook.view_model.WorkBookViewModel
import com.example.questionbook.view_model.WorkBookViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_list_workbook.*
import java.time.LocalDateTime


class WorkBookListFragment : Fragment() {

    //カテゴリーリストからタップされた項目番号。
    private var category:QuestionCategoryEntity? = null

    //サイドメニューから遷移した項目の値。
    private var type:Int = 0

    //adapter
    private var adapter:WorkBookListAdapter? = null

    //view model
    private val  viewModel:WorkBookViewModel by lazy {
        WorkBookViewModelFactory(activity?.application!!)
            .create(WorkBookViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.get(CategoryListFragment.ARGS_KEY) as QuestionCategoryEntity
            type = it.get(CategoryListFragment.ARGS_SIDE_MENU_FLAG) as Int
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = WorkBookListAdapter(category?.categoryTitle?:"",type){
            view, obj->
            val bundle = Bundle().apply { putParcelable(ARGS_KEY,obj) }
            //サイドメニューから項目をタップした時に、その項目の値によって遷移先を変える。
            type.actionWorkBook(view,bundle)
        }
        return inflater.inflate(R.layout.fragment_list_workbook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleInit()

        val categoryNo = category?.categoryNo?:0

        viewModel.data.observe(viewLifecycleOwner) {
            data->
            adapter?.submitList(
                    data.filter {
                        it.workBookEntity.relationCategory == categoryNo
                    }.toList()
            )}

        // 問題集一覧から追加ボタンを押した際に、ダイヤログを表示。
        workbook_add_fab.also {
            it.isVisible = type.isHolder()
            it.setOnClickListener {
                executeDialog()
            }
        }
    }

    private fun recycleInit(){
        val myLayoutManager = LinearLayoutManager(activity).apply { orientation= LinearLayoutManager.VERTICAL }
        val itemDecoration  = DividerItemDecoration(activity,myLayoutManager.orientation)
        work_book_recycle_view.also {
            it.adapter = adapter
            it.layoutManager = myLayoutManager
            it.addItemDecoration(itemDecoration)
        }
    }

    private fun executeDialog(){
        val dialogWorkBook = activity?.let {
            WorkBookDialogFactory(it,R.layout.dialog_category_layout)
                .create(WorkBookDialog::class.java)
        }

        dialogWorkBook?.let{ dg ->
            val alertDialog = dg.create().also { it.show() }
            val (title,btn) = dg.getView().findViewById<TextInputEditText>(R.id.dialog_category_title_edit) to
                    dg.getView().findViewById<Button>(R.id.dialog_category_insert_btn)
            btn.setOnClickListener {
                viewModel.toInsert(title.text.toString())
                alertDialog.cancel()
            }
        }
    }

    private fun WorkBookViewModel.toInsert(title:String){
        if (category == null) return
           insert(
                   QuestionWorkBookEntity(
                           workBookNo = 0,
                           workBookDate = LocalDateTime.now(),
                           workBookTitle = title,
                           workBookFlag = 0,
                           relationCategory = category?.categoryNo?:0
                   ))
    }

    companion object {
        const val ARGS_KEY = "navigate_args_workBook_to_text"
    }
}