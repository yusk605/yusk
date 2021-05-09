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
import com.example.questionbook.adapter.LeafListAdapter
import com.example.questionbook.dialog.*
import com.example.questionbook.room.QuestionLeafEntity
import com.example.questionbook.room.WorkBookWithAll
import com.example.questionbook.view_model.LeafListViewModel
import com.example.questionbook.view_model.LeafListViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.dialog_text_form_layout.*
import kotlinx.android.synthetic.main.fragment_list_leaf.*
import java.time.LocalDateTime


class LeafListFragment : Fragment() {

    //WorkBookFragmentから遷移されたときに渡される値として。
    private var workBookWithTextAndAccuracy:WorkBookWithAll? = null

    //view model
    private val viewModel:LeafListViewModel by lazy {
        LeafListViewModelFactory(activity?.application!!)
                .create(LeafListViewModel::class.java)
    }

    //アダプター
    private val quizAdapter:LeafListAdapter by lazy {
        LeafListAdapter{ entity->
            val quizDialog = activity?.let { it1 ->
                PageQuizDialogFactory(it1,R.layout.dialog_text_form_layout)
                        .create(PageQuizDialog::class.java) }

            quizDialog?.let { dg->
                val alertDialog = dg.create().apply {
                        setTitle(requireActivity().getString(R.string.dialog_leaf_updata_title))
                        show()
                    }

                val dialogView  = dg.getView()
                val quizButton  = dialogView.findViewById<Button>(R.id.form_quiz_add_btn).apply { text = activity?.getString(R.string.dialog_update_button) }
                dialogView.setParameter(entity)
                quizButton.setOnClickListener {
                    viewModel.leafUpdate(dialogView.getParameter(entity = entity).entity)
                    alertDialog.cancel()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workBookWithTextAndAccuracy =
                it.get(WorkBookListFragment.ARGS_KEY) as WorkBookWithAll
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_leaf, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //リサイクラービューの初期化
        initRecycleView()

        //ビューモデルからデータを取得し観測を行う。
        viewModel.leafEntityList.observe(viewLifecycleOwner){
            data->
            quizAdapter.submitList(
                data.filter {
                    it.relationWorkBook ==
                        workBookWithTextAndAccuracy?.workBookEntity?.workBookNo
                })
        }

        //クイズテキスト一覧に表示されている、ボタンを押したときの処理
        leaf_list_add_fab.setOnClickListener { v ->
            val quizDialog = activity?.let { it1 ->
                PageQuizDialogFactory(it1,R.layout.dialog_text_form_layout)
                        .create(PageQuizDialog::class.java)
            }
            quizDialog?.let { dg->
                val alertDialog     = dg.create().apply { show() }
                val quizView        = dg.getView()
                val quizButton      = quizView.findViewById<Button>(R.id.form_quiz_add_btn).apply { text = activity?.getString(R.string.dialog_insert_button) }
                quizButton.setOnClickListener {
                    viewModel.leafInsert(quizView.getParameter().entity)
                        alertDialog.cancel()
                    }
                }
            }
        }

    /**
     * リサイクラービューでの初期化を行うメソッド。
     */
    private fun initRecycleView(){
        leaf_list_recycle_view.also {
            it.layoutManager = LinearLayoutManager(activity).apply { orientation = LinearLayoutManager.VERTICAL }
            it.adapter = quizAdapter
        }
    }

    /**
     * ■ダイヤログのレイアウトに指定されている、ウィジェットを取得するメソッド。
     * @param dialogView ダイヤログに指定されているビューを引き数として渡す。
     */
    private fun View.getParameter():QuestionItem{

        val dialogStatement     = findViewById<TextInputEditText>(R.id.form_quiz_statement)
        val dialogFirst         = findViewById<TextInputEditText>(R.id.form_quiz_answer_first)
        val dialogTextSecond    = findViewById<TextInputEditText>(R.id.form_quiz_answer_second)
        val dialogTextThird     = findViewById<TextInputEditText>(R.id.form_quiz_answer_third)
        val dialogTextRight     = findViewById<TextInputEditText>(R.id.form_quiz_answer_right)

        return QuestionItem(
                questionTitle = workBookWithTextAndAccuracy?.workBookEntity?.workBookTitle?:"",
                selectAnswers = mutableListOf(),
                answerCheck = 0,
                entity = QuestionLeafEntity(
                                leafNo          = 0,
                                leafFirs        = dialogFirst.text.toString(),
                                leafSecond      = dialogTextSecond.text.toString(),
                                leafThird       = dialogTextThird.text.toString(),
                                leafRight       = dialogTextRight.text.toString(),
                                leafStatement   = dialogStatement.text.toString(),
                                leafCommentary  = "",
                                leafFlag = 0,
                                timeStamp = LocalDateTime.now(),
                                relationWorkBook = workBookWithTextAndAccuracy?.workBookEntity?.workBookNo?:0
                ))
    }
    private fun View.getParameter(entity: QuestionLeafEntity):QuestionItem{

        val dialogStatement     = findViewById<TextInputEditText>(R.id.form_quiz_statement)
        val dialogFirst         = findViewById<TextInputEditText>(R.id.form_quiz_answer_first)
        val dialogTextSecond    = findViewById<TextInputEditText>(R.id.form_quiz_answer_second)
        val dialogTextThird     = findViewById<TextInputEditText>(R.id.form_quiz_answer_third)
        val dialogTextRight     = findViewById<TextInputEditText>(R.id.form_quiz_answer_right)

        return QuestionItem(
                questionTitle = workBookWithTextAndAccuracy?.workBookEntity?.workBookTitle?:"",
                selectAnswers = mutableListOf(),
                answerCheck = 0,
                entity = entity.also {
                    it.leafFirs = dialogFirst.text.toString()
                    it.leafSecond = dialogTextSecond.text.toString()
                    it.leafThird = dialogTextThird.text.toString()
                    it.leafRight = dialogTextRight.text.toString()
                    it.leafStatement = dialogStatement.text.toString()
                    it.leafCommentary = ""
                    it.leafFlag = 0
                    it.timeStamp = LocalDateTime.now()
                    it.relationWorkBook = workBookWithTextAndAccuracy?.workBookEntity?.workBookNo ?: 0
                })
    }

    /**
     * ■値をセットする際に呼び出すメソッド
     * 問題一覧画面のスパナの ico をタップした際に予備さされるメソッド
     * その際にダイヤログに修正を行うデータを表示させること。
     * @param entity QuestionQuizEntity クイズのデータを格納する値。
     */
    private fun View.setParameter(entity:QuestionLeafEntity){
        findViewById<TextInputEditText>(R.id.form_quiz_statement).setText(entity.leafStatement)
        findViewById<TextInputEditText>(R.id.form_quiz_answer_second).setText(entity.leafSecond)
        findViewById<TextInputEditText>(R.id.form_quiz_answer_first).setText(entity.leafFirs)
        findViewById<TextInputEditText>(R.id.form_quiz_answer_third).setText(entity.leafThird)
        findViewById<TextInputEditText>(R.id.form_quiz_answer_right).setText(entity.leafRight)
    }
}