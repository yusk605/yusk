package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.questionbook.QuizResult
import com.example.questionbook.R
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment() {

    //クイズゲームを行った時に渡される値
    private lateinit var resultItem:QuizResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            resultItem = it.get(GameStartFragment.ARGS_KEY) as QuizResult
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultItem?.let { it.set() }

        concrete_result_button.setOnClickListener {
            Navigation.findNavController(it).navigate(
                    R.id.action_resultFragment_to_concreteResultFragment,
                    Bundle().apply { putInt(SAFE_ARGS_KEY,resultItem.relationWorkBookNo) }
            )
        }
    }

    private fun QuizResult.set(){
        fragment_result_title_text.text = resultTitle
        result_result_text.text         = resultText
        result_progress_bar.progress    = resultProgress
        result_progress_accuracy.text   = "${resultAccuracy.toInt()}%"
    }

    companion object{
        const val SAFE_ARGS_KEY = "ResultFragment to ConcreteResultFragment"
    }
}