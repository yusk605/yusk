package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.questionbook.QuestionItem
import com.example.questionbook.databinding.FragmentGameStartBinding
import com.example.questionbook.room.WorkBookWithAll


class GameStartFragment : Fragment() {

    private lateinit var binding:FragmentGameStartBinding

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

            }
    }
