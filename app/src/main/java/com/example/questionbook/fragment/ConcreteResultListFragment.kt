package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.questionbook.R
import kotlinx.android.synthetic.main.fragment_concrete_result_list.*


class ConcreteResultListFragment : Fragment() {

    private var workBookNo:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workBookNo = it.getInt(ResultFragment.SAFE_ARGS_KEY)
        }
    }

    override fun onCreateView( inflater: LayoutInflater,
                               container: ViewGroup?,
                               savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_concrete_result_list, container, false)
    }

    private fun recycleInit(){
        recyclerview_concrete_result.also {
            recyclerView ->
            recyclerView.adapter
            recyclerView.layoutManager =
                    LinearLayoutManager(activity).apply { orientation == LinearLayoutManager.VERTICAL }

        }
    }

    companion object {
    }
}