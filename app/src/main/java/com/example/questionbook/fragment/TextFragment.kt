
package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.questionbook.R
import com.example.questionbook.adapter.ProblemAdapter
import com.example.questionbook.view_model.TextViewModel
import com.example.questionbook.view_model.TextViewModelFactory
import kotlinx.android.synthetic.main.fragment_problem_list.*


class TextFragment : Fragment() {

    private lateinit var adapter:ProblemAdapter

    private val problemViewModel:TextViewModel by lazy {
        TextViewModelFactory(app = activity?.application!!)
            .create(TextViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        adapter = ProblemAdapter{btn,entiry-> }

        problemViewModel.data.observe(viewLifecycleOwner){
            data-> adapter.submitList(data)
        }

        initRecycleView()

        return inflater.inflate(R.layout.fragment_problem, container, false)

    }

    /**
     * リサイクラービューでの初期化を行うメソッド
     */
    private fun initRecycleView(){
        problem_recycle_view.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(activity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }
    }
}