package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.adapter.CategoryAdapter
import com.example.questionbook.room.QuestionDatabase
import com.example.questionbook.view_model.CategoryViewModel
import com.example.questionbook.view_model.CategoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_category.*


class CategoryFragment : Fragment() {

    /**
     * アダプタークラスの作成。
     * 項目をタップされた場合に、
     * タップされた項目に紐づいた問題集画面へ遷移を行う。
     */
    private val adapter: CategoryAdapter by lazy {
        CategoryAdapter { entity,view ->
            Navigation.findNavController(view).navigate(
                    R.id.action_categoryFragment_to_workBookFragment,
                    Bundle().apply { putParcelable(ARGS_KEY,entity) }
            )
        }
    }

    private val viewModel:CategoryViewModel by lazy {
        CategoryViewModelFactory(app = activity?.application!!)
                .create(CategoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycleInit()
        viewModel.let { vm ->
            vm.data.observe(viewLifecycleOwner){ data ->
                adapter.submitList(data)
            }
        }
        category_fab_add_button.setOnClickListener {

        }
    }

    private fun recycleInit(){
        category_recycle_view.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(activity)
        }
    }

    companion object{
        const val ARGS_KEY = "navigate_args_category_to_workBook"
    }
}