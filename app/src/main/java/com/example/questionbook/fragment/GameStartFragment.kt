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
import com.example.questionbook.room.QuestionAccuracyEntity
import com.example.questionbook.room.QuestionHistoryEntity
import com.example.questionbook.room.WorkBookWithAll
import com.example.questionbook.view_model.QuizGameViewModel
import com.example.questionbook.view_model.QuizGameViewModelFactory
import kotlinx.android.synthetic.main.fragment_game_start.*
import java.time.LocalDate


class GameStartFragment : Fragment() {

    private lateinit var binding:FragmentGameStartBinding
    private var workBookNo:Int = 0
    private var accuracyNo:Int = 0
    private var questionIt:QuestionItemIterator? = null
    private var questionItemShelf:QuestionItemShelf? = null
    private var questionItem:QuestionItem? = null
    private var radioButton:RadioButton? = null

    private var questionItemList:MutableList<QuestionItem> = mutableListOf()

    private val viewModel:QuizGameViewModel by lazy {
        QuizGameViewModelFactory(app = activity?.application!!)
                .create(QuizGameViewModel::class.java)
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

        workBookWithAll?.let {
            workBookNo = it.workBookEntity.workBookNo
            accuracyNo = it.accuracyList.filter { f->f.relationWorkBook == it.workBookEntity.workBookNo }.size +1
        }

        binding = FragmentGameStartBinding.inflate(inflater,container,false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            var selectAnswer = ""

            viewModel.quizEntityList.observe(viewLifecycleOwner){
                data->
                //イテレーターパターンを使用したクイズの集合体インスタンス
                questionItemShelf = QuestionItemShelf(
                        data.filter { it.relationWorkBook == workBookNo },
                        workBookWithAll?.workBookEntity?.workBookTitle?:""
                    )

                //クイズとなる集合体を数える役割を果たすイテレーターを取得
                questionIt = questionItemShelf?.let{ it.createIterator() }

                questionIt?.let {
                    questionItem = it.next()        //始めに出題されるクイズのデータを取得する。
                    questionItem?.set()             //取得したクイズデータをレイアウトファイルに表維持させる。
                    questionItemList.add(questionItem!!)
                    game_start_quiz_count.text = "${it.getIndex()}/${it.getSize()}"
                }

                //選択されたラジオボタンの値を取得する。
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
                            //q.historyInsert()
                        }
                    }
                    questionIt?.let{
                        it.nextQuiz(view)
                        game_start_quiz_count.text = "${it.getIndex()}/${it.getSize()}"
                    }
                }
            }
        }

    /**
     *  ■ ゲームを行った履歴を保存するためのメソッド。
     * @param QuestionItem
     */
    private fun QuestionItem.historyInsert(){
        viewModel.historyInsert(
                QuestionHistoryEntity(
                        historyNo = 0,
                        historyDate = LocalDate.now(),
                        historyRate = answerCheck,
                        relationQuiz = entity.quizNo,
                        relationAccuracy = accuracyNo
                )
        )
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

            val questionResult = questionItem?.setResult()

            //回答率を保存するため
            viewModel.accuracyInsert(
                    QuestionAccuracyEntity(
                            accuracyNo       = 0,
                            accuracyDate     = LocalDate.now(),
                            accuracyRate     = questionResult?.resultAccuracy?:0.toFloat(),
                            relationWorkBook = workBookNo
                    ))
        /*
            //履歴を残すためのロジック
            questionItemList.forEach {
                viewModel.historyInsert(
                        QuestionHistoryEntity(
                                historyNo = 0,
                                historyRate = it.answerCheck,
                                historyDate = LocalDate.now(),
                                relationQuiz = it.entity.quizNo
                        ))
            }*/

            val bundle = Bundle().apply { putParcelable(ARGS_KEY,questionResult) }

            Navigation.findNavController(view).navigate(R.id.action_gameStartFragment_to_resultFragment,bundle)
        }
    }


    /**
     * ■クイズの結果を表示させるための準備
     * 拡張関数に指定されいる QuestionItem
     * 単一で指定されている理由としては、
     * 遷移先に必要となる値が、questionTitle workBookNo
     * の二つの値をなるので、クイズを行った全データを必要としないため。　
     * @return リザルト画面での必要となるオブジェクトを返す
     */
    private fun QuestionItem.setResult():QuizResult{
        val quizCount           = questionItemList.size
        val quizAnswerCount     = questionItemList.filter { it.answerCheck==1 }.size
        val quizAccuracy:Float  = (quizAnswerCount.toFloat()/quizCount.toFloat())*100
        return QuizResult(
                resultTitle         =  questionTitle,
                resultText          =  "${quizCount}問中${quizAnswerCount}問正解",
                resultAccuracy      =  quizAccuracy,
                resultProgress      =  quizAccuracy.toInt(),
                relationWorkBookNo  =  workBookNo
        )
    }

    /**
     * ■パラメーターをテキストの値に持たせる。
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
