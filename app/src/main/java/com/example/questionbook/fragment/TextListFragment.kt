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
import com.google.android.material.chip.Chip
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
            makeDialog()
        }
    }

    private fun makeDialog(){

        val textDialog = activity?.let {
            TextDialogFactory(it,R.layout.dialog_text_form_layout)
                    .create(TextDialog::class.java)
            }

        textDialog?.also { dialog->

            //ダイヤログを生成するためのオブジェクトを取得。
            var alertDialog = dialog.create().apply { show() }

            //ダイヤログ内に指定さているビューを表示。
            val dialogView  = dialog.getView()

            //ダイヤログ内に指定されているボタンを表示。
            val button = dialogView.findViewById<Button>(R.id.form_text_form_update_btn)

            //ダイヤログに表示されている項目の値を取得。
            val data = getParameter(dialogView)

            button.setOnClickListener {
                viewModel.data.observe(viewLifecycleOwner) { entity->
                    insertFirst(data)
                    (entity.last().textEntity.textNo+1).insertSecond(data)
                    alertDialog.cancel()
                }
            }
        }
    }

    private fun dialogShow(){
        val (textDialog,answerDialog) =
            TextInsertDialogFactory(requireActivity(),R.layout.dialog_form_filst_text_layout).create(TextInsertDialog::class.java) to
                    AnswerDialogFactory(requireActivity(),R.layout.dialog_answer_insert_layout).create(AnswerInsertDialog::class.java)

        //TODO ダイヤログの処理を記述すること

    }

    private fun initRecycleView(){
        text_recycle_view.also {
            it.layoutManager = LinearLayoutManager(activity).apply { orientation = LinearLayoutManager.VERTICAL }
            it.adapter = adapter
        }
    }

    /**
     * ■ダイヤログのレイアウトに指定されている、ウィジェットを取得するメソッド。
     * @param dialogView ダイヤログに指定されているビューを引き数として渡す。
     */
    private fun getParameter(dialogView: View):QuestionItem{
       // val dialogButton        = dialogView.findViewById<Button>(R.id.dialog_text_form_update_btn)
        val dialogStatement     = dialogView.findViewById<TextInputEditText>(R.id.form_text_statement_edit)
        val dialogFirst         = dialogView.findViewById<TextInputEditText>(R.id.form_text_answer_first)
        val dialogTextSecond    = dialogView.findViewById<TextInputEditText>(R.id.form_text_answer_second)
        val dialogTextThird     = dialogView.findViewById<TextInputEditText>(R.id.form_text_answer_third)
        val dialogTextRight     = dialogView.findViewById<TextInputEditText>(R.id.form_text_answer_right)
        val dialogChip          = dialogView.findViewById<Chip>(R.id.dialog_text_chip)

        return QuestionItem(
                questionStatement   = dialogStatement.text.toString(),
                questionFirs        = dialogFirst.text.toString(),
                questionSecond      = dialogTextSecond.text.toString(),
                questionThird       = dialogTextThird.text.toString(),
                questionRight       = dialogTextRight.text.toString(),
                questionTitle       = dialogChip.text.toString()
        )
    }

    private fun insertFirst(data:QuestionItem){
        synchronized(this){
            viewModel.textInsert(  QuestionTextEntity(
                    textNo           = 0,
                    textStatement    = data.questionStatement,
                    textFlag         = 0,
                    timeStamp        = LocalDateTime.now(),
                    relationWorkBook = workBookWithTextAndAccuracy?.workBookEntity?.workBookNo?:0
            )) }
    }

    private fun Int.insertSecond(data: QuestionItem){
        synchronized(this){
                viewModel.insertAnswer(
                        QuestionAnswerEntity(
                                answerNo        = 0,
                                answerFirs      = data.questionFirs,
                                answerSecond    = data.questionSecond,
                                answerThird     = data.questionThird,
                                answerRight     = data.questionRight,
                                relationText    = this
                        )) }
    }
}