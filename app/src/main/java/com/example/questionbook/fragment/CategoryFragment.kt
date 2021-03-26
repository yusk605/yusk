package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.adapter.CategoryAdapter
import com.example.questionbook.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = CategoryAdapter { entity,view ->
            //カテゴリーリストからアイテムをタップした場合に、
            //カテゴリーに紐づいたワークブックをを表示させる画面へ遷移する。
            Navigation.findNavController(view).navigate(
                    R.id.action_categoryFragment_to_workBookFragment,
                    Bundle().apply { putInt(ARGS_KEY,entity.categoryNo) }
            )
        }
    }

    private fun recycleInit(){ }

    companion object{
        const val ARGS_KEY = "navigate_args_category_to_workBook"
    }

}