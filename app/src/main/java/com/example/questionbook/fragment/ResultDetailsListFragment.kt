package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.questionbook.QuizResult
import com.example.questionbook.R
import com.example.questionbook.adapter.ResultDetailsListAdapter
import com.example.questionbook.view_model.ResultViewModel
import com.example.questionbook.view_model.ResultViewModelFactory
import kotlinx.android.synthetic.main.fragment_list_result_details.*


class ResultDetailsListFragment : Fragment() {

    private var quizResult:QuizResult? = null

    private val adapter:ResultDetailsListAdapter by lazy {
        ResultDetailsListAdapter(quizResult?.resultTitle?:"")
    }

    private val viewModel: ResultViewModel by lazy {
        ResultViewModelFactory(activity?.application!!).create(ResultViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            quizResult = it.get(ResultFragment.SAFE_ARGS_KEY) as QuizResult
        }
    }

    override fun onCreateView( inflater: LayoutInflater,
                               container: ViewGroup?,
                               savedInstanceState: Bundle? ): View? {

        viewModel.quizWithHistory.observe(viewLifecycleOwner){
            data->
            adapter.submitList(
                    data.filter { f-> f.historyEntity.relationAccuracy == quizResult?.relationAccuracyNo}
                        .sortedBy { it.historyEntity.historyLeafNumber }
                        .map { it.historyEntity }
                        .toList()
            )
        }
        return inflater.inflate(R.layout.fragment_list_result_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycleInit()
    }

    private fun recycleInit(){
        concrete_result_recycler_view.also {
            recyclerView ->
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                    LinearLayoutManager(activity).apply { orientation == LinearLayoutManager.VERTICAL }
        }
    }

    companion object {
    }
}