package com.example.questionbook.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import androidx.fragment.app.Fragment
import com.example.questionbook.R
import com.example.questionbook.view_model.GarbageCanViewModel
import com.example.questionbook.view_model.GarbageCanViewModelFactory
import kotlinx.android.synthetic.main.fragment_garbage_can_list.*

class GarbageCanListFragment : Fragment() {

    private var typeAction = 0

    private var checkedList:MutableList<Int> = mutableListOf()

    private val viewModel:GarbageCanViewModel by lazy {
        GarbageCanViewModelFactory(activity?.application!!)
            .create(GarbageCanViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeAction = it.getInt(GarbageCanFragment.ARGS_KEY)
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

        garbage_can_choice_list.let {
            listView ->
            listView.setOnItemClickListener {
                parent, view, position, id ->

                var checkedTextView: CheckedTextView

                for ( i in 0 until listView.childCount ) {
                    checkedTextView = listView.getChildAt(i) as CheckedTextView
                    val position = listView.getPositionForView(checkedTextView)
                    if ( checkedTextView.isChecked ){
                        //既にチェックのついた項目の値を重複させないようにするため
                        if( !checkedList.contains(position)) {
                            checkedList.add(position)
                        }
                    }else{
                        //チェック項目を付けた後の値のみを省きたいため。
                        if(checkedList.contains(position)){
                            checkedList.remove(position)
                        }
                    }
                }
            }
        }
    }

    /**
     * ▪渡される値によってリストビューのアダプターを変更する。
     */
    private fun initArrayAdapter(){
        when(typeAction){
            1 -> {
                viewModel.categoryList.observe(viewLifecycleOwner){
                    data ->
                    garbage_can_choice_list.adapter = getArrayAdapter(
                            data.filter { it.categoryFlag == 2 }
                                    .map { it.categoryTitle }
                                    .toList()
                    )

                    val categoryList = data.filter { it.categoryFlag == 2 }

                    //削除ボタンを押したときにチェック項目のついた箇所を全て削除する。
                    garbage_can_delete_button.setOnClickListener {
                        checkedList.forEach { p ->
                            viewModel.deleteCategory(categoryList[p])
                        }
                        //リストの要素をクリアする。
                        checkedList.clear()
                    }
                    garbage_can_return_button.setOnClickListener {
                        checkedList.forEach { p ->
                            viewModel.upDateCategory(categoryList[p].apply { categoryFlag = 0 })
                        }
                        //リストの要素をクリアする。
                        checkedList.clear()
                    }
                }}
            2 -> {
                viewModel.workBookList.observe(viewLifecycleOwner){
                    data ->
                    garbage_can_choice_list.adapter = getArrayAdapter(
                        data.filter { it.workBookFlag == 2 }
                            .map { it.workBookTitle }
                            .toList()
                    )

                    val workBookList = data.filter { it.workBookFlag == 2 }

                    //削除ボタンを押したときにチェック項目のついた箇所をすべて削除
                    garbage_can_delete_button.setOnClickListener {
                        checkedList.forEach { p->
                            viewModel.deleteWorkBook(workBookList[p])
                        }
                        //リストの要素をクリアする。
                        checkedList.clear()
                    }
                    garbage_can_return_button.setOnClickListener {
                        checkedList.forEach { p->
                            viewModel.upDateWorkBook(workBookList[p].apply { workBookFlag = 0 })
                        }
                        //リストの要素をクリアする。
                        checkedList.clear()
                    }
                }}
            3 -> {
                viewModel.leafList.observe(viewLifecycleOwner){
                    data ->
                    garbage_can_choice_list.adapter = getArrayAdapter(
                        data.filter { it.leafFlag == 2 }
                            .map { it.leafStatement }
                            .toList()
                    )

                    val leafList = data.filter { it.leafFlag == 2 }

                    garbage_can_delete_button.setOnClickListener {
                        checkedList.forEach { p->
                            viewModel.deleteLeaf(leafList[p])
                        }
                        checkedList.clear()
                    }
                    garbage_can_return_button.setOnClickListener {
                        checkedList.forEach { p->
                            viewModel.upDateLeaf(leafList[p].apply { leafFlag = 0 })
                        }
                        checkedList.clear()
                    }
                }}

            else -> throw IllegalAccessException(" selectAdapter not value ")
        }
    }

    private fun getArrayAdapter(data:List<String>):ArrayAdapter<String>{
        return ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_multiple_choice,
                data
        )
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = GarbageCanListFragment()
    }
}