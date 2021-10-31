package com.example.questionbook.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.questionbook.*
import com.example.questionbook.databinding.FragmentGameStartBinding
import com.example.questionbook.logic.QuestionItemIterator
import com.example.questionbook.logic.QuestionItemShelf
import com.example.questionbook.room.QuestionAccuracyEntity
import com.example.questionbook.room.QuestionHistoryEntity
import com.example.questionbook.room.QuestionLeafEntity
import com.example.questionbook.room.WorkBookWithAll
import com.example.questionbook.view_model.QuizGameViewModel
import com.example.questionbook.view_model.QuizGameViewModelFactory
import kotlinx.android.synthetic.main.fragment_game_start.*
import java.time.LocalDateTime


class GameStartFragment : Fragment() {

    private lateinit var binding:FragmentGameStartBinding

    //クイズ表示・保存に必要な値
    private var workBookNo:Int = 0
    private var accuracyNo:Int = 0

    //クイズのロジックを行うための必要なインスタンスを準備
    private var questionIt:QuestionItemIterator? = null         //イテレータパターンでの反復子
    private var questionItemShelf:QuestionItemShelf? = null     //イテレータパターンでの集合体
    private var questionItem:QuestionItem? = null               //クイズを表示するためのオブジェクト
    private var radioButton:RadioButton? = null                 //ラジオボタン（選択案）
    private val answers:MutableList<String> = mutableListOf()   //選択する解答を位置維持的に保持する。

    //クイズを行ったデータを格納するためのリスト。
    private var questionItemList:MutableList<QuestionItem> = mutableListOf()

    private var flag:Boolean = false

    private val viewModel:QuizGameViewModel by lazy {
        QuizGameViewModelFactory(app = activity?.application!!)
                .create(QuizGameViewModel::class.java)
    }

    //WorkBookFragment から遷移された際に渡される値。
    private var workBookWithAll:WorkBookWithAll? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workBookWithAll = it.get(WorkBookListFragment.ARGS_KEY) as WorkBookWithAll
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        workBookWithAll?.let {
            workBookNo = it.workBookEntity.workBookNo
           // accuracyNo = if (it.accuracyList.isNotEmpty())it.accuracyList.last().accuracyNo+1 else 1
        }
        viewModel.accuracyEntityList.observe(viewLifecycleOwner){
            data->
            when (data.isNotEmpty()) {
                true    ->   accuracyNo = data.last().accuracyNo + 1
            }
        }

        binding = FragmentGameStartBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            broker(view)

            var selectAnswer = ""

            viewModel.quizEntityList.observe(viewLifecycleOwner){
                data->
                //イテレーターパターンを使用した集合体（アグリゲート）インスタンスを取得
                questionItemShelf = QuestionItemShelf(
                        data.filter { it.relationWorkBook == workBookNo }
                                .filter { it.leafFlag!=2 },
                        workBookWithAll?.workBookEntity?.workBookTitle?:""
                    )

                //イテレータパターンを使用した集合体の反復子（イテレーター）インスタンスを取得
                questionIt = questionItemShelf?.let{ it.createIterator() }

                questionIt?.let {
                    //始めに出題されるクイズのデータを取得し選択する回答となる文字をリストに保持する。
                    questionItem = it.next().also { q->addAnswers(q.entity) }

                    questionItem?.set()             //取得したクイズデータをレイアウトファイルに表維持させる。
                    questionItemList.add(questionItem!!)
                    game_start_quiz_count.text = "${it.getIndex()}/${it.getSize()}"
                }

                game_start_next_btn.isEnabled = false;

                //選択されたラジオボタンの値を取得する。
                game_start__radio_group.setOnCheckedChangeListener {
                    group, checkedId ->
                    radioButton = group.findViewById<RadioButton>(checkedId)
                    selectAnswer = radioButton?.text.toString()
                    game_start_next_btn.isEnabled = true
                }

                //次のボタンを押したときの処理
                game_start_next_btn.setOnClickListener {
                    view ->
                    var select = selectAnswer
                    game_start__radio_group.clearCheck()  //ラジオボタンにチェックを外すメソッド
                    questionItemShelf?.let { shelf->
                        questionItem?.let { q->
                            q.selectAnswer = select                 //履歴のエンティティに保存するため
                            shelf.incorrectAnswerCount(q,select)    //正解カウントを行う
                            shelf.correctAnswerCount(q,select)      //不正解カウントを行う
                        }
                    }
                    //次のクイズを表示する
                    questionIt?.let{
                        it.nextQuiz(view)
                        game_start_quiz_count.text = "${it.getIndex()}/${it.getSize()}"
                    }
                    game_start_next_btn.isEnabled = false
                }
            }
        }

    /**
     * バックスタックに移動した際、各バッキングフィールドに初期化を行う
     */
    override fun onDestroyView() {
        super.onDestroyView()
        //下記のフラグは private fun broker(view: View) メソッド内で使用されています。
        flag = true
        questionItemList    = mutableListOf()
        questionItem        = null
        questionIt          = null
        questionItemShelf   = null
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
                //次の問題の選択する文字を格納する。
                addAnswers(it.entity)
                it.set()
                questionItemList.add(it)
            }
        }else{

            val questionResult = questionItem?.setResult()

            //回答率を保存するため
            viewModel.accuracyInsert(
                    QuestionAccuracyEntity(
                            accuracyNo       = 0,
                            accuracyRate     = questionResult?.resultAccuracy?:0.toFloat(),
                            timeStamp        = LocalDateTime.now(),
                            relationWorkBook = workBookNo,
                            accuracyFlag = 0))

            questionItemList.forEach { it.historyInsert() }

            val bundle = Bundle().apply {
                putParcelable(ARGS_KEY,questionResult)
            }

            Navigation.findNavController(view).navigate(R.id.action_gameStartFragment_to_resultFragment,bundle)
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
                        timeStamp = LocalDateTime.now(),
                        historyCheck = answerCheck,
                        historyLeafNumber = historyQuizNumber,
                        historyLeafSelectAnswer = selectAnswer,
                        historyLeafFirst = entity.leafFirs,
                        historyLeafSecond = entity.leafSecond,
                        historyLeafThird = entity.leafThird,
                        historyLeafRate = entity.leafRight,
                        historyLeafStatement = entity.leafStatement,
                        relationLeaf = entity.leafNo,
                        relationAccuracy = accuracyNo
                )
            )
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
                relationWorkBookNo  =  workBookNo,
                relationAccuracyNo  = accuracyNo
        )
    }

    /**
     * ■パラメーターをテキストの値に持たせる。
     */
    private fun QuestionItem.set(){
        game_start_quiz_statement.setText(entity.leafStatement)
        game_start_radio_button_first.text    = answers[0]
        game_start_radio_button_second.text   = answers[1]
        game_start_radio_button_third.text    = answers[2]
        game_start_radio_button_force.text    = answers[3]
    }

    /**
     * 選択する回答をシャッフルして表示させるため
     */
    private fun addAnswers(entity: QuestionLeafEntity){
        answers.clear()
        answers.add(entity.leafFirs)
        answers.add(entity.leafSecond)
        answers.add(entity.leafThird)
        answers.add(entity.leafRight)
        answers.shuffle()
    }

    /**
     * ■一つ前に戻るアクションで生じるバグを防ぐためのメソッド
     * ゲームクイズを終了した際にリザルト画面へ遷移した時に、
     * もう一度前の画面へ戻りクイズゲームを終了させるとリザルト画面の結果が
     * 意図した結果とならないため、クイズゲームからリザルト画面へ遷移を行った時は一度カテゴリー一覧へ
     * 戻る処理を行うメソッド。
     */
    private fun broker(view: View){
        val navController = Navigation.findNavController(view)
        val bundle =  newBundleToPutInt(resources.getStringArray(R.array.side_menu_keys)[1], MainActivity.actionGameValue )
        if (flag){
            navController.navigate(R.id.action_gameStartFragment_to_categoryFragment,bundle)
                .run{ flag =false }
        }
    }

    companion object{
        const val ARGS_KEY = "GameStartFragment to ResultFragment"
    }
}
