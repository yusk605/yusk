package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.adapter.TextAdapter
import com.example.questionbook.room.TextWithAnswerAndHistory
import com.example.questionbook.room.WorkBookWithTextAndAccuracy
import com.example.questionbook.view_model.TextViewModel
import com.example.questionbook.view_model.TextViewModelFactory
import kotlinx.android.synthetic.main.fragment_text_list.*


class TextListFragment : Fragment() {

    //WorkBookFragmentから遷移されたときに渡される値として。
    private var textWithAnswerAndHistory:WorkBookWithTextAndAccuracy? = null

    //view model の生成を行うこと。
    private val viewModel:TextViewModel by lazy {
        TextViewModelFactory(activity?.application!!).create(TextViewModel::class.java)
    }

    //
    private val adapter:TextAdapter by lazy { TextAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            textWithAnswerAndHistory =
                it.get(WorkBookFragment.ARGS_KEY) as WorkBookWithTextAndAccuracy
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_text_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        viewModel.data.observe(viewLifecycleOwner){data->
            adapter.submitList(
                data.filter {
                    it.textEntity.relationWorkBook ==
                        textWithAnswerAndHistory?.workBookEntity?.workBookNo }
            )
        }
    }

    private fun initRecycleView(){
        text_recycle_view.also {
            it.layoutManager = LinearLayoutManager(activity).apply { orientation = LinearLayoutManager.VERTICAL }
            it.adapter = adapter
        }
    }
}