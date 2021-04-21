package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.questionbook.R
import com.example.questionbook.ResultItem

class ResultFragment : Fragment() {

    //クイズゲームを行った時に渡される値
    private lateinit var resultItem:ResultItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            resultItem = it.get(GameStartFragment.ARGS_KEY) as ResultItem
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object{
        const val SAFE_ARGS_KEY = "ResultFragment to ConcreteResultFragment"
    }

}