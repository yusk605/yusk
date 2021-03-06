package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.questionbook.QuizResult
import com.example.questionbook.R
import com.example.questionbook.room.QuestionHistoryEntity
import com.example.questionbook.view_model.HistoryViewModel
import com.example.questionbook.view_model.HistoryViewModelFactory
import com.example.questionbook.view_model.QuizGameViewModel
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment() {

    //クイズゲームを行った時に渡される値
    private lateinit var resultItem:QuizResult
    private val viewModel: HistoryViewModel by lazy {
        HistoryViewModelFactory(requireActivity().application)
            .create(HistoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            resultItem = it.get(GameStartFragment.ARGS_KEY) as QuizResult
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        viewModel.accuracyList.observe(viewLifecycleOwner){
//            accuracyNo = it.last().accuracyNo
//            resultItem.relationAccuracyNo = accuracyNo
//        }

        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultItem.set()

        result_details_button.setOnClickListener {
            Navigation.findNavController(it).navigate(
                    R.id.action_resultFragment_to_concreteResultFragment,
                    Bundle().apply { putParcelable(SAFE_ARGS_KEY,resultItem) }
            )
        }
    }

    private fun QuizResult.set(){
        result_title_label.text = resultTitle
        result_text.text         = resultText
        result_progress_bar.progress    = resultProgress
        result_progress_accuracy_rate.text   = "${resultAccuracy.toInt()}%"
    }

    companion object{
        const val SAFE_ARGS_KEY = "ResultFragment to ConcreteResultFragment"
    }
}