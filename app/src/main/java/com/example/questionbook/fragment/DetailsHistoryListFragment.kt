package com.example.questionbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.adapter.ResultDetailsListAdapter
import com.example.questionbook.fragment.HistoryListFragment
import com.example.questionbook.view_model.ResultViewModel
import com.example.questionbook.view_model.ResultViewModelFactory
import kotlinx.android.synthetic.main.fragment_details_history_list.*


class DetailsHistoryListFragment : Fragment() {

    private var accuracyNo:Int =0
    private var workBookTitle:String = ""

    private val adapter:ResultDetailsListAdapter by lazy {
        ResultDetailsListAdapter(title = workBookTitle)
    }

    private val viewModel:ResultViewModel by lazy {
        ResultViewModelFactory(
            requireActivity().application
        ).create(ResultViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accuracyNo = it.getInt(HistoryListFragment.SAFE_ARGS_ACCURACY_NO)
            workBookTitle = it.getString(HistoryListFragment.SAFE_ARGS_ACCURACY_TITLE).toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel.quizWithHistory.observe(viewLifecycleOwner){
            data->
            adapter.submitList(
                data.filter { it.historyEntity.relationAccuracy == accuracyNo }.toList()
            )
        }
        return inflater.inflate(R.layout.fragment_details_history_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        history_details_recyclerview.init()
    }

    private fun RecyclerView.init() =
        this.also { rv->
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireActivity())
            .apply { orientation = LinearLayoutManager.VERTICAL }
    }


    companion object {
    }
}