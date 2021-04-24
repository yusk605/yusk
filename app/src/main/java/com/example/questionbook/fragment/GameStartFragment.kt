package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.Navigation
import com.example.questionbook.QuestionItem
import com.example.questionbook.QuizResult
import com.example.questionbook.R
import com.example.questionbook.databinding.FragmentGameStartBinding
import com.example.questionbook.logic.QuestionItemIterator
import com.example.questionbook.logic.QuestionItemShelf
import com.example.questionbook.room.WorkBookWithAll
import com.example.questionbook.view_model.QuizViewModel
import com.example.questionbook.view_model.QuizViewModelFactory
import kotlinx.android.synthetic.main.fragment_game_start.*


class GameStartFragment : Fragment() {

    private lateinit var binding:FragmentGameStartBinding
    private var questionIt:QuestionItemIterator? = null
    private var questionItemShelf:QuestionItemShelf? = null
    private var questionItem:QuestionItem? = null
    private var radioButton:RadioButton? = null

    private var questionItemList:MutableList<QuestionItem> = mutableListOf()

    private val viewModel:QuizViewModel by lazy {
        QuizViewModelFactory(app = activity?.application!!)
                .create(QuizViewModel::class.java)
    }

    private var workBookWithAll:WorkBookWithAll? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workBookWithAll = it.get(WorkBookFragment.ARGS_KEY) as WorkBookWithAll
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGameStartBinding.inflate(inflater,container,false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            var selectAnswer = ""
            viewModel.data.observe(viewLifecycleOwner){
                data->
                questionItemShelf = QuestionItemShelf(
                        data.filter { it.relationWorkBook == workBookWithAll?.workBookEntity?.workBookNo },
                        workBookWithAll?.workBookEntity?.workBookTitle?:""
                    )
                questionIt = questionItemShelf?.let{ it.createIterator() }
                questionIt?.let {
                    questionItem = it.next()
                    questionItem?.set()
                    questionItemList.add(questionItem!!)
                    game_start_quiz_count.text = "${it.getIndex()}/${it.getSize()}"
                }


                game_radio_group.setOnCheckedChangeListener {
                    group, checkedId ->
                    radioButton = group.findViewById<RadioButton>(checkedId)
                    selectAnswer = radioButton?.text.toString()
                }

                //次のボタンを押したときの処理
                game_answer_btn.setOnClickListener { view ->
                    var select = selectAnswer
                    game_radio_group.clearCheck()  //ラジオボタンにチェックを外すメソッド
                    questionItemShelf?.let { shelf->
                        questionItem?.let { q->
                            shelf.incorrectAnswerCount(q,select)
                            shelf.correctAnswerCount(q,select)
                        } }
                    questionIt?.let{
                        it.nextQuiz(view)
                        game_start_quiz_count.text = "${it.getIndex()}/${it.getSize()}"
                    }
                }
            }
        }

    /**
     * ■次の問題集が存在している場合は次の問題を表示。
     * @param view                  現在のビューを渡すこと。
     * @param QuestionItemIterator  クイズのデータを数え上げるためのオブジェクト。
     * クイズとなる問題を表示させるためのメソッド。
     * ランダムに表示させた問題
     */
    private fun QuestionItemIterator.nextQuiz(view: View){
        if(hasNext()) {
            questionItem = next()
            questionItem?.let {
                it.set()
                questionItemList.add(it)
            }
        }else{
            val bundle = Bundle().apply { putParcelable(ARGS_KEY,questionItem?.setResult()) }
            Navigation.findNavController(view).navigate(R.id.action_gameStartFragment_to_resultFragment,bundle)
        }
    }

    private fun QuestionItem.setResult():QuizResult{
        val quizCount           = questionItemList.size
        val quizAnswerCount     = questionItemList.filter { it.answerCheck==1 }.size
        val quizAccuracy:Float  = (quizAnswerCount.toFloat()/quizCount.toFloat())*100
        return QuizResult(
                resultTitle = questionTitle,
                resultText = "${quizCount}問中${quizAnswerCount}問正解",
                resultAccuracy = quizAccuracy,
                resultProgress = quizAccuracy.toInt()
        )
    }

    /**
     * ■ラジオボタンから選択した文字列を返す
     * @return 文字列
     */
    private fun getSelectAnswer():String{
        var selectAnswer = ""
        game_radio_group.setOnCheckedChangeListener {
            group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            selectAnswer = radioButton.text.toString()
        }
        return selectAnswer
    }

    /**
     * ■パラメーターをダイヤログに値を持たせる。
     */
    private fun QuestionItem.set(){
        item_list_quiz_statement_edit.setText(entity.quizStatement)
        game_radio_button_first.text    = selectAnswers[0]
        game_radio_button_second.text   = selectAnswers[1]
        game_radio_button_third.text    = selectAnswers[2]
        game_radio_button_force.text    = selectAnswers[3]
    }
    companion object{
        const val ARGS_KEY = "GameStartFragment to ResultFragment"
    }
}
