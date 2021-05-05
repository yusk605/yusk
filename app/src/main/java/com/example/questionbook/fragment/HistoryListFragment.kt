package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.R
import com.example.questionbook.adapter.HistoryListAdapter
import com.example.questionbook.createPopup
import com.example.questionbook.room.QuestionAccuracyEntity
import com.example.questionbook.room.WorkBookWithAll
import com.example.questionbook.view_model.HistoryViewModel
import com.example.questionbook.view_model.HistoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_list_history.*


class HistoryListFragment : Fragment() {

    private var workBookWithAll:WorkBookWithAll? = null

    private val adapter:HistoryListAdapter by lazy {
        HistoryListAdapter{ v,e->
            val bundle = Bundle().apply {
                putInt(SAFE_ARGS_ACCURACY_NO,e.accuracyNo)
                putString(SAFE_ARGS_WORKBOOK_TITLE,workBookWithAll?.workBookEntity?.workBookTitle)
            }
            val navController = Navigation.findNavController(v)
            showPopup(v,navController,bundle,e)
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


    private fun showPopup(
            view:View,
            navController:NavController,
            bundle:Bundle,
            entity:QuestionAccuracyEntity
    ){
        createPopup(
                PopupMenu(requireActivity(),view),
                R.menu.menu_hisotry_popup
        ).setOnMenuItemClickListener {
          return@setOnMenuItemClickListener when(it.itemId){
                R.id.popup_menu_history_select_first  -> {
                    navController.navigate(
                            R.id.action_historyListFragment_to_detailsHistoryListFragment,
                            bundle
                    )
                    true
                }
                R.id.popup_menu_history_select_second -> {
                    viewModel.accuracyDelete(entity)
                    true
                }
              else -> {
                   false
                }
            }
        }
    }

    companion object {
        const val SAFE_ARGS_ACCURACY_NO     = "safe_args_accuracy_no_received_details_history"
        const val SAFE_ARGS_WORKBOOK_TITLE  = "safe_args_workbook_title_received_details_history"
    }
}