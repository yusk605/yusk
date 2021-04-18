package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.questionbook.QuestionItem
import com.example.questionbook.R
import com.example.questionbook.adapter.QuizAdapter
import com.example.questionbook.dialog.*
import com.example.questionbook.room.QuestionQuizEntity
import com.example.questionbook.room.WorkBookWithAll
import com.example.questionbook.room.WorkBookWithTextAndAccuracy
import com.example.questionbook.view_model.QuizViewModel
import com.example.questionbook.view_model.QuizViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_quiz_text_list.*
import java.time.LocalDateTime


class QuizListFragment : Fragment() {

    //WorkBookFragmentから遷移されたときに渡される値として。
    private var workBookWithTextAndAccuracy:WorkBookWithAll? = null

    //view model の生成を行うこと。
    private val viewModel:QuizViewModel by lazy {
        QuizViewModelFactory(activity?.application!!)
                .create(QuizViewModel::class.java)
    }

    private val quizAdapter:QuizAdapter by lazy { QuizAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workBookWithTextAndAccuracy =
                it.get(WorkBookFragment.ARGS_KEY) as WorkBookWithAll
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz_text_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycleView()
        viewModel.data.observe(viewLifecycleOwner){ data->
            quizAdapter.submitList(
                data.filter {
                    it.relationWorkBook ==
                        workBookWithTextAndAccuracy?.workBookEntity?.workBookNo
                })
        }

        fab_text_add.setOnClickListener { v->
            val quizDialog = activity?.let { it1 ->
                PageQuizDialogFactory(it1,R.layout.dialog_text_form_layout)
                        .create(PageQuizDialog::class.java)
            }

            quizDialog?.let { dg->
                val alertDialog     = dg.create().apply { show() }
                val quizView        = dg.getView()
                val quizButton      = quizView.findViewById<Button>(R.id.form_quiz_add_btn)
                quizButton.setOnClickListener { b->
                    val questionItem    = getParameter(quizView)
                    viewModel.insert(
                            QuestionQuizEntity(
                                    quizNo          = 0,
                                    quizFirs        = questionItem.questionFirs,
                                    quizSecond      = questionItem.questionSecond,
                                    quizThird       = questionItem.questionThird,
                                    quizRight       = questionItem.questionRight,
                                    quizStatement   = questionItem.questionStatement,
                                    quizCommentary  = "",
                                    quizAnswerCheck = 0,
                                    timeStamp = LocalDateTime.now(),
                                    relationWorkBook = workBookWithTextAndAccuracy?.workBookEntity?.workBookNo?:0
                            ))
                        alertDialog.cancel()
                    }
                }
            }
        }




    /**
     * リサイクラービューでの初期化を行うメソッド。
     */
    private fun initRecycleView(){
        text_recycle_view.also {
            it.layoutManager = LinearLayoutManager(activity).apply { orientation = LinearLayoutManager.VERTICAL }
            it.adapter = quizAdapter
        }
    }

    /**
     * ■ダイヤログのレイアウトに指定されている、ウィジェットを取得するメソッド。
     * @param dialogView ダイヤログに指定されているビューを引き数として渡す。
     */
    private fun getParameter(dialogView: View):QuestionItem{

        val dialogStatement     = dialogView.findViewById<TextInputEditText>(R.id.form_quiz_statement)
        val dialogFirst         = dialogView.findViewById<TextInputEditText>(R.id.form_quiz_answer_first)
        val dialogTextSecond    = dialogView.findViewById<TextInputEditText>(R.id.form_quiz_answer_second)
        val dialogTextThird     = dialogView.findViewById<TextInputEditText>(R.id.form_quiz_answer_third)
        val dialogTextRight     = dialogView.findViewById<TextInputEditText>(R.id.form_quiz_answer_right)

        return QuestionItem(
                questionStatement   = dialogStatement.text.toString(),
                questionFirs        = dialogFirst.text.toString(),
                questionSecond      = dialogTextSecond.text.toString(),
                questionThird       = dialogTextThird.text.toString(),
                questionRight       = dialogTextRight.text.toString(),
                questionTitle       = workBookWithTextAndAccuracy?.workBookEntity?.workBookTitle?:"",
                selectAnswers = mutableListOf()
        )
    }
}