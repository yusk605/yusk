package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.questionbook.R
import kotlinx.android.synthetic.main.fragment_laboratory.*

class LaboratoryFragment : Fragment() {

    //TODO ViewModel（Category,WorkBook）を作成を行うこと。
    //TODO ダイヤログの作成を行うこと。

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_laboratory, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        laboratory_register_category.setOnClickListener {  }
        laboratory_register_workbook.setOnClickListener {  }



    }
}