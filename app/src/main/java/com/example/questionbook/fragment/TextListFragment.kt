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
import com.example.questionbook.adapter.TextAdapter
import com.example.questionbook.dialog.*
import com.example.questionbook.room.QuestionAnswerEntity
import com.example.questionbook.room.QuestionTextEntity
import com.example.questionbook.room.WorkBookWithTextAndAccuracy
import com.example.questionbook.view_model.TextViewModel
import com.example.questionbook.view_model.TextViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_text_list.*
import java.time.LocalDateTime


class TextListFragment : Fragment() {

    //WorkBookFragmentから遷移されたときに渡される値として。
    private var workBookWithTextAndAccuracy:WorkBookWithTextAndAccuracy? = null

    //view model の生成を行うこと。
    private val viewModel:TextViewModel by lazy {
        TextViewModelFactory(activity?.application!!).create(TextViewModel::class.java)
    }

    private val adapter:TextAdapter by lazy { TextAdapter{ view,obj-> } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workBookWithTextAndAccuracy =
                it.get(WorkBookFragment.ARGS_KEY) as WorkBookWithTextAndAccuracy
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_text_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        viewModel.data.observe(viewLifecycleOwner){data->
            adapter.submitList(
                data.filter {
                    it.textEntity.relationWorkBook ==
                        workBookWithTextAndAccuracy?.workBookEntity?.workBookNo
                })
        }
        fab_text_add.setOnClickListener {
            dialogShow()
        }
    }


    private fun dialogShow(){
        val (textDialog,answerDialog) =
            TextInsertDialogFactory(requireActivity(),R.layout.dialog_form_filst_text_layout).create(TextInsertDialog::class.java) to
                    AnswerDialogFactory(requireActivity(),R.layout.dialog_answer_insert_layout).create(AnswerInsertDialog::class.java)

        //ダイヤログを表示させる処理。
        val alertDialogText = textDialog.create().apply { show() }

        //テキストダイヤログのビューを取得する。
        val dialogTextView = textDialog.getView()

        //ダイヤログ内に保持されているViewを取得する。
        val (text,textButton) = dialogTextView.findViewById<TextInputEditText>(R.id.form_first_text_statement_edit) to
                                dialogTextView.findViewById<Button>(R.id.dialog_text_insert)

        var flag = false

        //ダイヤログでの最初のインサートを行う。
        textButton.setOnClickListener {

            var statement = text.text.toString()

            textDialog.create()

            val dialogAnswer = answerDialog.create().apply { show() }

            val answerDialogView = answerDialog.getView()

            val item = getParameter(answerDialogView,statement)

            val button = answerDialogView.findViewById<Button>(R.id.form_answer_button_add)

            viewModel.textInsert(
                    QuestionTextEntity(
                            textNo           = 0,
                            textStatement    = statement,
                            textFlag         = 0,
                            timeStamp        = LocalDateTime.now(),
                            relationWorkBook = workBookWithTextAndAccuracy?.workBookEntity?.workBookNo?:0
                    ))

            button.setOnClickListener {
                val dialogFirst         = answerDialogView.findViewById<TextInputEditText>(R.id.form_answer_answer_first)
                val dialogTextSecond    = answerDialogView.findViewById<TextInputEditText>(R.id.form_answer_answer_second)
                val dialogTextThird     = answerDialogView.findViewById<TextInputEditText>(R.id.form_answer_answer_third)
                val dialogTextRight     = answerDialogView.findViewById<TextInputEditText>(R.id.form_answer_answer_right)

                viewModel.insertAnswer(
                        QuestionAnswerEntity(
                                answerNo        = 0,
                                answerFirs      = dialogFirst.text.toString(),
                                answerSecond    = dialogTextSecond.text.toString(),
                                answerThird     = dialogTextThird.text.toString(),
                                answerRight     = dialogTextRight.text.toString(),
                                relationText    = 0
                        )
                    )
            }
        }
        //TODO ダイヤログの処理を記述すること
    }

    /**
     * リサイクラービューでの初期化を行うメソッド。
     */
    private fun initRecycleView(){
        text_recycle_view.also {
            it.layoutManager = LinearLayoutManager(activity).apply { orientation = LinearLayoutManager.VERTICAL }
            it.adapter = adapter
        }
    }

    /**
     * ■ダイヤログのレイアウトに指定されている、ウィジェットを取得するメソッド。
     * @param dialogView ダイヤログに指定されているビューを引き数として渡す。
     * @param statement  問題文となる文字列
     */
    private fun getParameter(dialogView: View,statement:String):QuestionItem{

        val dialogFirst         = dialogView.findViewById<TextInputEditText>(R.id.form_answer_answer_first)
        val dialogTextSecond    = dialogView.findViewById<TextInputEditText>(R.id.form_answer_answer_second)
        val dialogTextThird     = dialogView.findViewById<TextInputEditText>(R.id.form_answer_answer_third)
        val dialogTextRight     = dialogView.findViewById<TextInputEditText>(R.id.form_answer_answer_right)

        return QuestionItem(
                questionStatement   = statement,
                questionFirs        = dialogFirst.text.toString(),
                questionSecond      = dialogTextSecond.text.toString(),
                questionThird       = dialogTextThird.text.toString(),
                questionRight       = dialogTextRight.text.toString(),
                questionTitle       = workBookWithTextAndAccuracy?.workBookEntity?.workBookTitle?:""
        )
    }
}