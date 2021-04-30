package com.example.questionbook.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.questionbook.R
import com.example.questionbook.view_model.WorkBookViewModel
import com.example.questionbook.view_model.WorkBookViewModelFactory
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.android.synthetic.main.fragment_statistics_details.*


class StatisticsDetailsFragment : Fragment() {

    private var workBookNo = 0

    private val viewModel:WorkBookViewModel by lazy {
        WorkBookViewModelFactory(activity?.application!!)
                .create(WorkBookViewModel::class.java)
    }

    private val entryList = mutableListOf<BarEntry>()
    private val barDataSets = mutableListOf<IBarDataSet>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workBookNo = it.getInt(StatisticsFragment.SAFE_ARGS_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statistics_details,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner){
            all->
                var index = 0f
                all.first { it.workBookEntity.workBookNo == workBookNo }
                        .accuracyList.forEach {
                            a-> entryList.add(BarEntry(index, a.accuracyRate))
                            index += 1f
                        }
                set()
            }
    }
    private fun set(){
      val barDataSet = BarDataSet(entryList, "linear").also {
            it.color = Color.BLUE
      }
        barDataSets.add(barDataSet)

        val barData = BarData(barDataSets)

        statistics_charts_barChartExample.apply {
            data = barData
            animateY(1000, Easing.EaseInOutQuad)
            xAxis.also {
                x->
                x.isEnabled = true
                x.textColor = Color.BLACK
            }
        }.invalidate()
    }
}