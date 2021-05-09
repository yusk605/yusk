package com.example.questionbook.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.GarbageCanActionButton
import com.example.questionbook.R
import com.example.questionbook.room.QuestionCategoryEntity
import com.example.questionbook.room.QuestionLeafEntity
import com.example.questionbook.room.QuestionWorkBookEntity
import com.example.questionbook.view_model.GarbageCanViewModel
import com.example.questionbook.view_model.GarbageCanViewModelFactory
import kotlinx.android.synthetic.main.fragment_garbage_can_list.*

class GarbageCanListFragment : Fragment() {

    private var typeAction = 0
    private var adapter: RecyclerView.Adapter<*>? = null

    private var arrayAdapter:ArrayAdapter<String>? = null

    private val viewModel:GarbageCanViewModel by lazy {
        GarbageCanViewModelFactory(requireActivity().application)
            .create(GarbageCanViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeAction = it.getInt(
                GarbageCanActionButton
                    .CATEGORY
                    .name
                    .hashCode()
                    .toString()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_garbage_can_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArrayAdapter()
        val listView = garbage_can_choice_list
        listView.adapter = arrayAdapter
        listView.setOnItemClickListener {
                parent, view, position, id ->

            var checkedTextView:CheckedTextView =
                listView.getChildAt(position) as CheckedTextView

            if (checkedTextView.isChecked){

            }
        }

    }

    private fun getArrayAdapter(data:List<String>):ArrayAdapter<String>{
        return ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_multiple_choice,
            data
        )
    }

    private fun initArrayAdapter(){
        when(typeAction){
            1 -> {
                viewModel.categoryList.observe(viewLifecycleOwner){
                    data ->
                    arrayAdapter = getArrayAdapter(data.map { it.categoryTitle }.toList())
                }
            }
            2 -> {
                viewModel.workBookList.observe(viewLifecycleOwner){
                    data ->
                    arrayAdapter = getArrayAdapter(data.map { it.workBookTitle }.toList())
                }
            }
            3 -> {
                viewModel.leafList.observe(viewLifecycleOwner){
                    data ->
                    arrayAdapter = getArrayAdapter(data.map { it.leafStatement }.toList())
                }
            }
            else -> throw IllegalAccessException(" selectAdapter not value ")
        }
    }

    private fun  initListView(){
        garbage_can_choice_list.adapter = arrayAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = GarbageCanListFragment()
    }
}