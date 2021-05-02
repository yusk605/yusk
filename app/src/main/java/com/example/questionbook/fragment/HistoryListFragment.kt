package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.adapter.HistoryListAdapter
import com.example.questionbook.room.WorkBookWithAll
import com.example.questionbook.view_model.HistoryViewModel
import com.example.questionbook.view_model.HistoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_list_history.*


class HistoryListFragment : Fragment() {

    private var workBookWithAll:WorkBookWithAll? = null

    private val adapter:HistoryListAdapter by lazy {
        HistoryListAdapter{ v,no->
            val bundle = Bundle().apply {
                putInt(SAFE_ARGS_ACCURACY_NO,no)
                putString(SAFE_ARGS_WORKBOOK_TITLE,workBookWithAll?.workBookEntity?.workBookTitle)
            }
            Navigation.findNavController(v)
                .navigate(R.id.action_historyListFragment_to_detailsHistoryListFragment,bundle)
        }
    }

    private val viewModel:HistoryViewModel by lazy {
        HistoryViewModelFactory(requireActivity().application)
            .create(HistoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workBookWithAll = it.get(WorkBookListFragment.ARGS_KEY) as WorkBookWithAll
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.accuracyList.observe(viewLifecycleOwner){
            data->
            adapter.submitList(
                data.filter { it.relationWorkBook ==
                        workBookWithAll?.workBookEntity?.workBookNo
                }.toList()
            )
        }
        history_recycle_view_chip.text = workBookWithAll?.workBookEntity?.workBookTitle
        history_recycle_view.recycleInit()
    }

    private fun RecyclerView.recycleInit(){
        adapter = this@HistoryListFragment.adapter
        layoutManager = GridLayoutManager(requireActivity(),2,GridLayoutManager.VERTICAL,false)
     }


    companion object {
        const val SAFE_ARGS_ACCURACY_NO     = "safe_args_accuracy_no_received_details_history"
        const val SAFE_ARGS_WORKBOOK_TITLE  = "safe_args_workbook_title_received_details_history"
    }
}