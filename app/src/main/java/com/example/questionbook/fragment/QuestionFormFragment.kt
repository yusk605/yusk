package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.questionbook.R
import com.example.questionbook.room.QuestionProblemEntity
import com.example.questionbook.view_model.QuestionFormViewModel
import com.example.questionbook.view_model.QuestionViewModelFactory
import kotlinx.android.synthetic.main.fragment_problem_insert.*
import java.time.LocalDateTime

class QuestionFormFragment : Fragment() {

    private val formViewModel:QuestionFormViewModel by lazy {
        QuestionViewModelFactory(app = activity?.application!!)
            .create(QuestionFormViewModel::class.java)
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

        form_problem_btn.setOnClickListener {

        }
    }
    private fun setProblemEntity() = QuestionProblemEntity(
        problemNo = 0,
        problemFlag = 0,
        relationWorkBook = 0,
        timeStamp = LocalDateTime.now(),
        problemStatement = form_problem_statement_edit.text.toString()
    )
}