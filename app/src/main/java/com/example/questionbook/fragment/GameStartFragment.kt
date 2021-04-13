package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.questionbook.QuestionItem
import com.example.questionbook.R
import com.example.questionbook.databinding.FragmentGameStartBinding
import com.example.questionbook.room.QuestionWorkBookEntity
import com.example.questionbook.room.WorkBookWithTextAndAccuracy
import com.example.questionbook.view_model.TextViewModel
import com.example.questionbook.view_model.TextViewModelFactory
import com.example.questionbook.view_model.WorkBookViewModel
import com.example.questionbook.view_model.WorkBookViewModelFactory


class GameStartFragment : Fragment() {

    private lateinit var binding:FragmentGameStartBinding

    private var questions:List<QuestionItem>? = null
    private var createQuestion:QuestionItem? = null
    private var answers:MutableList<String> = mutableListOf()

    private val viewModel:TextViewModel by lazy {
        TextViewModelFactory(app = activity?.application!!)
                .create(TextViewModel::class.java)
    }

    private var workBookEntity:QuestionWorkBookEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workBookEntity =
                    (it.get(WorkBookFragment.ARGS_KEY) as WorkBookWithTextAndAccuracy).workBookEntity
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
        viewModel.data.observe(viewLifecycleOwner){
            data->
            questions = data.filter { it.textEntity.relationWorkBook == workBookEntity?.workBookNo }
                        .map { QuestionItem(
                                questionTitle       = workBookEntity?.workBookTitle?:"",
                                questionStatement   = it.textEntity.textStatement,
                                questionFirs        = it.answer.answerFirs,
                                questionSecond      = it.answer.answerSecond,
                                questionThird       = it.answer.answerThird,
                                questionRight       = it.answer .answerRight) }
                        .toList()

            data.filter { it.textEntity.relationWorkBook == workBookEntity?.workBookNo }
                    .map { it.answer }
                    .forEach {}


            //単一のクイズデータを取得する。
            createQuestion = questions?.let{ it.shuffled().first()  }

            createQuestion?.let{
                it.setQuestionText(it.getShuffledAnswer())
            }
        }
    }

    //ランダムで取得したクイズデータを問題文として表示させる。
    private fun QuestionItem.setQuestionText(answer:List<String>){
        binding.also {
            it.dialogTextStatementEdit.setText(questionStatement)
            it.gameRadioButtonFirst.text    = answer[0]
            it.gameRadioButtonSecond.text   = answer[1]
            it.gameRadioButtonThird.text    = answer[2]
            it.gameRadioButtonFourth.text   = answer[3]
        }
    }

    //解答案の文字列。
    private fun QuestionItem.getShuffledAnswer()=
            answers.also {
                it.add(questionRight)
                it.add(questionFirs)
                it.add(questionSecond)
                it.add(questionThird)
            }.shuffled()

    companion object {}
}