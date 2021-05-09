package com.example.questionbook.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.questionbook.GarbageCanActionButton
import com.example.questionbook.R

class GarbageCanListFragment : Fragment() {

    private var typeAction = 0

    private var adapter: RecyclerView.Adapter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeAction = it.getInt(
                GarbageCanActionButton
                    .CATEGORY
                    .name
                    .hashCode()
                    .toString()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_garbage_can_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun selectAdapter(){
        when(typeAction){
            1 -> {
            }
            2 -> {
            }
            3 -> {
            }
            else ->{    }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = GarbageCanListFragment()
    }
}