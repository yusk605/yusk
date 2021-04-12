package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.questionbook.R
import com.example.questionbook.room.QuestionAnswerEntity
import com.example.questionbook.room.QuestionCategoryEntity
import com.example.questionbook.room.QuestionTextEntity
import com.example.questionbook.view_model.*
import kotlinx.android.synthetic.main.fragment_text_insert.*
import java.time.LocalDateTime

class QuestionFormFragment : Fragment() {

    private val app = activity?.application

    private var itemCategory:String = ""
    private var itemWorkBook:String = ""

    private val workBookViewModel:WorkBookViewModel by lazy {
        WorkBookViewModelFactory(app=app!!)
            .create(WorkBookViewModel::class.java)
    }

    private val formViewModel:QuestionFormViewModel by lazy {
        QuestionViewModelFactory(app = app!!)
            .create(QuestionFormViewModel::class.java)
    }

    private val categoryViewModel:CategoryViewModel by lazy {
        CategoryViewModelFactory(app = app!!)
            .create(CategoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text_insert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryViewModel.data.observe(viewLifecycleOwner) { data ->

            form_problem_category_spinner.also{ sp->
                sp.setCategoryAdapter(
                    data.map { it.questionCategoryEntity }
                        .map { it.categoryTitle }
                        .toList()
                )
                sp.getSelectItem()
            }

           var categoryEntity:QuestionCategoryEntity = data
                    .asSequence()
                    .map { it.questionCategoryEntity }
                    .filter { it.categoryTitle == itemCategory }
                    .toList()
                    .first()

            form_problem_workbook_spinner.setWorkBookAdapter(
               data.filter { it.questionCategoryEntity == categoryEntity }
                   .map { it.workBookList }
                   .map { it.map { m->m.workBookTitle }.toList() }
                   .first()
            )
        }

        form_problem_btn.setOnClickListener {

        }
    }


    /**
     * ■スピナーのドロップリストが選択されているかどうかの判断
      */
    private fun isSelectSpinner():Boolean = itemCategory.isNotEmpty()

    /**
     * ■スピナーにボックスとなるリストを表示させるために必要な値を渡す。
     * @param list 文字例をリスト化したものをスピナーのアダプターに渡す。
     */
    private fun Spinner.setCategoryAdapter(list:List<String>){
        adapter = activity?.let {
            fragmentActivity ->
            ArrayAdapter(
                    fragmentActivity,
                    android.R.layout.simple_spinner_dropdown_item,
                    list
            )
        }
    }

    /**
     * ■カテゴリー選択するスピナーを選択した場合に、問題集となるスピナーを選択欄に動的に変化させる。
     * @param list 表示させたい問題集のドロップリスト一覧（文字列）
     */
    private fun Spinner.setWorkBookAdapter(list:List<String>){
        if ( isSelectSpinner() )
            setCategoryAdapter(list)

        return
    }

    /**
     * 選択された項目を取得する
     */
    private fun Spinner.getSelectItem(){
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val sp   = parent as? Spinner
                val itemSpinner = sp?.selectedItem as? String
                itemCategory = itemSpinner?:""
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
               itemCategory = ""
            }
        }
    }

    /**
     * ■問題集のエンティティに値を入れるメソッド。
     * 入力フォームに保持されている、問題文の値を取得する。
     * @param workBookNo 問題集の連番となる連番に紐づく番号
     * @return QuestionProblemEntity
     */
    private fun getProblemEntity(workBookNo:Int) = QuestionTextEntity(
        textNo = 0,
        textFlag = 0,
        relationWorkBook = workBookNo,
        timeStamp = LocalDateTime.now(),
        textStatement = dialog_text_statement_edit.text.toString()
    )

    /**
     * ■解答のテーブル内に値を入れるためのメソッド
     * 入力フォームに保持されている、解答案の値を取得する。
     * @param problemNo 問題のテキストとなる連番
     * @return QuestionAnswerEntity
     */
    private fun getAnswerEntity(problemNo:Int) = QuestionAnswerEntity(
        answerNo = 0,
        answerFirs =    dialog_text_answer_first.text.toString(),
        answerSecond =  dialog_text_answer_second.text.toString(),
        answerThird =   dialog_text_third.text.toString(),
        answerRight =   dialog_text_answer_right.text.toString(),
        relationText = problemNo
    )
}