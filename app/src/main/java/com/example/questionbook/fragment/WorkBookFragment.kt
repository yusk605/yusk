package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.adapter.WorkBookAdapter
import com.example.questionbook.room.QuestionCategoryEntity
import com.example.questionbook.view_model.WorkBookViewModel
import com.example.questionbook.view_model.WorkBookViewModelFactory
import kotlinx.android.synthetic.main.fragment_work_book.*


class WorkBookFragment : Fragment() {

    //カテゴリーリストからタップされた項目番号
    private var category:QuestionCategoryEntity? = null

    //アダプターの生成
    private val adapter:WorkBookAdapter by lazy {
        WorkBookAdapter(category?.categoryTitle?:""){ view,obj->
            val bundle=Bundle().apply { putParcelable(ARGS_KEY,obj) }
            Navigation.findNavController(view)
                .navigate(R.id.action_workBookFragment_to_problemListFragment,bundle)
        }
    }

    //view model の生成
    private val  viewModel:WorkBookViewModel by lazy {
        WorkBookViewModelFactory(activity?.application!!)
            .create(WorkBookViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.get(CategoryFragment.ARGS_KEY) as QuestionCategoryEntity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleInit()
        viewModel.data.observe(viewLifecycleOwner) { data->
            adapter.submitList(
                    data.filter {
                        it.workBookEntity.relationCategory == category?.categoryNo
                    }.toList()
            )
        }
    }

    private fun recycleInit(){

        val myLayoutManager = LinearLayoutManager(activity).apply { orientation= LinearLayoutManager.VERTICAL }
        val itemDecoration  = DividerItemDecoration(activity,myLayoutManager.orientation)

        recycle_view_work_book.also {
            it.adapter = adapter
            it.layoutManager = myLayoutManager
            it.addItemDecoration(itemDecoration)
        }
    }

    companion object {
        const val ARGS_KEY = "navigate_args_workBook_to_text"
    }

}