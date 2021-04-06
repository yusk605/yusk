
package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.adapter.ProblemAdapter
import com.example.questionbook.view_model.ProblemViewModel
import com.example.questionbook.view_model.ProblemViewModelFactory
import kotlinx.android.synthetic.main.fragment_problem_list.*


class ProblemFragment : Fragment() {

    private lateinit var adapter:ProblemAdapter

    private val problemViewModel:ProblemViewModel by lazy {
        ProblemViewModelFactory(app = activity?.application!!)
            .create(ProblemViewModel::class.java)
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