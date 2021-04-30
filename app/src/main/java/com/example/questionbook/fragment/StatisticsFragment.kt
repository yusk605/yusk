package com.example.questionbook.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.Navigation
import com.example.questionbook.R
import com.example.questionbook.room.QuestionCategoryEntity
import com.example.questionbook.view_model.WorkBookViewModel
import com.example.questionbook.view_model.WorkBookViewModelFactory
import com.github.mikephil.charting.animation.Easing.EaseInOutQuad
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_statistics.*

class StatisticsFragment : Fragment(){

    //カテゴリーリストからタップされた項目番号。
    private var category: QuestionCategoryEntity? = null
    private var type:Int = 0
    private var categoryNo =0

    //スピナーに値を動的に持たせるための処理
    private var titleList       = mutableListOf<String>()
    private var arrayAdapter:ArrayAdapter<String>? = null
    private var selectWorkBookNo = 0

    //チャートをデータを表示させるために必要なコレクション
    private var entryList:MutableList<PieEntry> = mutableListOf()

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
        categoryNo = category?.categoryNo?:0
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
        var sum = 0
        var accuracyRate=0f
        viewModel.data.observe(viewLifecycleOwner){
            data->
                data.filter { it.workBookEntity.relationCategory == categoryNo }
                    .forEach { all->
                        //解答率のデータが存在する場合は、
                        if (all.accuracyList.isNotEmpty())accuracyRate =
                                all.accuracyList.map { it.accuracyRate }.sum()/all.accuracyList.size.toFloat()

                        entryList.add(PieEntry(accuracyRate,all.workBookEntity.workBookTitle))
                        titleList.add(all.workBookEntity.workBookTitle)
                        sum += accuracyRate.toInt()
                    }

                val pieDataSet = createPieDataSet(entryList,"workBook_pieChart")

                createPieChart(PieData(pieDataSet))
                        .apply { centerText = "${sum}%" }
                        .transformLegend()
                        .invalidate()

                //スピナーに項目となる値を保持させるための初期化
                activity?.let { a-> statistics_spinner.initArrayAdapter(a.applicationContext,titleList) }

                statistics_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val sp = parent as  Spinner
                          selectWorkBookNo =
                                  data.filter{ it.workBookEntity.workBookTitle == sp.selectedItem.toString() }
                                        .map { it.workBookEntity.workBookNo }
                                        .first()
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        selectWorkBookNo = 0
                    }
                }
            }
            statistics_details_button.setOnClickListener {
                val bundle =Bundle().apply { putInt(SAFE_ARGS_KEY,selectWorkBookNo) }
                Navigation.findNavController(it)
                        .navigate(R.id.action_statisticsFragment_to_statistics_details,bundle)
            }
        }


    /**
     * 円グラフの状態を管理し生成するメソッド。
     * @param data PieData オブジェクト PieDataSet からインスタンスを生成してください。
     * @return PieCart　レイアウトファイルで指定されるウィジェット名
     **/
    private fun createPieChart(data:PieData):PieChart =
        statistics_pie_chart_example.also {
            it.data = data
            it.description.isEnabled=false                             //説明文
            it.setEntryLabelTextSize(16f)                              //チャートの文字の大きさを指定
            //it.centerText = "${sum}%"                                  //円グラフに表示される文字
            it.setCenterTextSize(30f)                                  //円グラフに表示される文字の大きさ
            it.setCenterTextColor(Color.BLACK)
            //  pie.setHoleColor(Color.rgb(117,199,236))                //中央の背景色
            //  pie.holeRadius = 30f                                    //中央の円の大きさ
            //  pie.transparentCircleRadius=35f                         //外側での円の大きさ
            it.isHighlightPerTapEnabled= true
            it.animateY(1000,EaseInOutQuad)                   //アニメーションを開始する
            it.setExtraOffsets(5f, 0f, 5f, 60f)   //グラフの配置を指定
            it.isDrawHoleEnabled = true
        }

    /**
     * 円グラフのデータ（表示される値）の状態を管理し生成するメソッド
     * @return PieDataSet　レイアウトファイルで指定されるウィジェット名
     */
    private fun createPieDataSet(data:List<PieEntry>,label:String):PieDataSet =
            PieDataSet(data,label).also {
               it.valueTextSize = 20f //円グラフに指定されている値の大きさを指定。
               it.colors = ColorTemplate.COLORFUL_COLORS.toList()
               it.sliceSpace = 3f
               it.selectionShift = 5f
               it.valueTextColor = Color.BLACK
            }

    /**
     * 表示される項目ラベルの状態をの状態を管理するメソッド
     * @return PieChart レイアウトファイルで指定されるウィジェット名
     */
    private fun PieChart.transformLegend() =
          this.apply {
              legend.also {
                  it.isEnabled = false                                             //表示・非表示
                  it.verticalAlignment = Legend.LegendVerticalAlignment.TOP        //文字の配置（垂直）
                  it.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT  //文字の配置（水平）
                  it.orientation = Legend.LegendOrientation.VERTICAL               //項目ラベルを垂直に並べる
                  it.setDrawInside(false)
                  it.xEntrySpace = 0f
                  it.yEntrySpace = 0f
                  it.yOffset = 0f
                  it.xOffset = 0f
              }
          }


              /**
               * バックスタックに移動した際にバッキングフィールドに残された値の初期化を行う。
               * 理由としては、このフラグメントから別のフラグメントへ遷移された際に、バッキングフィールドに
               * 保持された値が残ってしまうため。この場合は、画面を切り替えるたびにグラフの項目が増え続ける。
               * そのバグを回避するための初期処理として。
               */
              override fun onDestroyView() {
                  super.onDestroyView()
                  arrayAdapter = null
                  titleList = mutableListOf()
                  entryList = mutableListOf()
              }
              private fun Spinner.initArrayAdapter(context:Context, data:List<String>){
                  arrayAdapter = ArrayAdapter(
                          context,
                          R.layout.support_simple_spinner_dropdown_item,
                          data
                  )
                  this.adapter = arrayAdapter
              }
              companion object{
              const val SAFE_ARGS_KEY = "navigate_args_statisticsFragment_to_statistics_details"
          }

}