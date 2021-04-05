package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.questionbook.R
import com.example.questionbook.room.QuestionProblemEntity
import com.example.questionbook.view_model.*
import kotlinx.android.synthetic.main.fragment_problem_insert.*
import java.time.LocalDateTime

class QuestionFormFragment : Fragment() {

    private val app = activity?.application

    private val formViewModel:QuestionFormViewModel by lazy {
        QuestionViewModelFactory(app = app!!)
            .create(QuestionFormViewModel::class.java)
    }

    private val categoryViewModel:CategoryViewModel by lazy {
        CategoryViewModelFactory(app = app!!)
            .create(CategoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_problem_insert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryViewModel.data.observe(viewLifecycleOwner)
        { data ->
            form_problem_category_spinner.adapter =
                activity?.let {fragmentActivity->
                    ArrayAdapter(
                        fragmentActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        data.map { it.questionCategoryEntity }
                            .map { it.categoryTitle }
                            .toList() as Array<out String>
                    ) }

        }

        form_problem_btn.setOnClickListener {

        }
    }

    private fun get(){
        form_problem_category_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val sp   = parent as? Spinner
                val item = sp?.selectedItem as? String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun setSpinnerItem(){
        form_problem_workbook_spinner.adapter
    }

    private fun setProblemEntity() = QuestionProblemEntity(
        problemNo = 0,
        problemFlag = 0,
        relationWorkBook = 0,
        timeStamp = LocalDateTime.now(),
        problemStatement = form_problem_statement_edit.text.toString()
    )
}