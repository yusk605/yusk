package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.adapter.ResultDetailsListAdapter
import com.example.questionbook.view_model.HistoryViewModel
import com.example.questionbook.view_model.HistoryViewModelFactory
import com.example.questionbook.view_model.ResultViewModel
import com.example.questionbook.view_model.ResultViewModelFactory
import kotlinx.android.synthetic.main.fragment_details_history_list.*


class DetailsHistoryListFragment : Fragment() {

    private var accuracyNo:Int =0
    private var workBookTitle:String = ""

    private val adapter:ResultDetailsListAdapter by lazy {
        ResultDetailsListAdapter(title = workBookTitle)
    }

    private val viewModel:HistoryViewModel by lazy {
        HistoryViewModelFactory(
            requireActivity().application
        ).create(HistoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accuracyNo = it.getInt(HistoryListFragment.SAFE_ARGS_ACCURACY_NO)
            workBookTitle = it.getString(HistoryListFragment.SAFE_ARGS_WORKBOOK_TITLE).toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel.historyList.observe(viewLifecycleOwner){
            data->
            adapter.submitList(
                data.filter { it.relationAccuracy == accuracyNo }
                    .sortedBy { it.historyLeafNumber }
                    .toList()
            )
        }
        return inflater.inflate(R.layout.fragment_details_history_list, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycleViewInit()
    }


    private fun recycleViewInit() =
        history_details_recyclerview.let { rv->
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireActivity())
            .apply { orientation = LinearLayoutManager.VERTICAL }
    }


    companion object {
    }
}