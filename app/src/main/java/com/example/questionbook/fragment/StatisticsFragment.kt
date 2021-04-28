package com.example.questionbook.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.questionbook.R
import com.example.questionbook.room.QuestionCategoryEntity
import com.example.questionbook.room.WorkBookWithAll
import com.example.questionbook.view_model.WorkBookViewModel
import com.example.questionbook.view_model.WorkBookViewModelFactory
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_statistics.*

class StatisticsFragment : Fragment() {

    //カテゴリーリストからタップされた項目番号。
    private var category: QuestionCategoryEntity? = null
    private var type:Int = 0

    private val entryList:MutableList<PieEntry> = mutableListOf()


    private val viewModel:WorkBookViewModel by lazy {
        WorkBookViewModelFactory(activity?.application!!)
            .create(WorkBookViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.get(CategoryFragment.ARGS_KEY) as QuestionCategoryEntity
            type = it.get(CategoryFragment.ARGS_SIDE_MENU_FLAG) as Int
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //⑤PieChartにPieData格納
        var sum = 0
        viewModel.data.observe(viewLifecycleOwner){
                data->
            data.filter { it.workBookEntity.relationCategory == category?.categoryNo }
                .forEach { all->
                    entryList.add(
                        PieEntry(all.accuracyList
                            .map { it.accuracyRate }
                            .sum()/all.accuracyList.size.toFloat(),
                            all.workBookEntity.workBookTitle
                        ))
                    sum += all.accuracyList.map { it.accuracyRate }.sum().toInt()
                }

            val pieDataSet = PieDataSet(entryList, "candle")
                .apply {
                    valueTextSize = 20f
                }
            pieDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
            val pieData = PieData(pieDataSet)

            statistics_pie_chart_example.also {
                    pie->
                pie.data = pieData
                pie.legend.isEnabled = false
                pie.description.isEnabled=false
                pie.invalidate()
                pie.setEntryLabelTextSize(16f)
                pie.centerText = "${sum}%"
                pie.setCenterTextSize(30f)
                pie.setHoleColor(Color.rgb(117,199,236))
                pie.holeRadius = 30f
                pie.transparentCircleRadius=35f
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatisticsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}