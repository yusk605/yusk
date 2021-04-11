
package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.questionbook.R
import com.example.questionbook.adapter.TextAdapter
import com.example.questionbook.view_model.TextViewModel
import com.example.questionbook.view_model.TextViewModelFactory
import kotlinx.android.synthetic.main.fragment_text_list.*


class TextFragment : Fragment() {

    private lateinit var adapter:TextAdapter

    private val problemViewModel:TextViewModel by lazy {
        TextViewModelFactory(app = activity?.application!!)
            .create(TextViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_text, container, false)

    }

    /**
     * リサイクラービューでの初期化を行うメソッド
     */
    private fun initRecycleView(){
        text_recycle_view.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(activity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }
    }
}