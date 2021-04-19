package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.questionbook.QuestionItem
import com.example.questionbook.databinding.FragmentGameStartBinding
import com.example.questionbook.logic.ConcreteQuizGameStartLogic
import com.example.questionbook.room.WorkBookWithAll
import com.example.questionbook.view_model.QuizViewModel
import com.example.questionbook.view_model.QuizViewModelFactory
import kotlinx.android.synthetic.main.fragment_game_start.*


class GameStartFragment : Fragment() {

    private lateinit var binding:FragmentGameStartBinding

    private lateinit var logic: ConcreteQuizGameStartLogic

    private val viewModel:QuizViewModel by lazy {
        QuizViewModelFactory(app = activity?.application!!)
                .create(QuizViewModel::class.java)
    }

    private var questions:List<QuestionItem>? = null
    private var createQuestion:QuestionItem? = null
    private var answers:MutableList<String> = mutableListOf()

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
            viewModel.data.observe(viewLifecycleOwner){
                data->
                logic =
                        ConcreteQuizGameStartLogic(
                                data, workBookWithAll?.workBookEntity?.workBookTitle?:""
                        )
                logic.getRandom().first().set()

            }
        }

    private fun QuestionItem.set(){
        game_start_statement_edit.setText(questionStatement)
        game_radio_button_first.text    = selectAnswers[0]
        game_radio_button_second.text   = selectAnswers[1]
        game_radio_button_third.text    = selectAnswers[2]
        game_radio_button_force.text    = selectAnswers[3]
    }
}
