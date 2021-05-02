package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.questionbook.*
import com.example.questionbook.adapter.CategoryListAdapter
import com.example.questionbook.dialog.CategoryDialog
import com.example.questionbook.dialog.CategoryDialogFactory
import com.example.questionbook.room.QuestionCategoryEntity
import com.example.questionbook.view_model.CategoryViewModel
import com.example.questionbook.view_model.CategoryViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_list_category.*
import java.time.LocalDateTime


class CategoryListFragment : Fragment() {

    private var type = 0

    //アダプタークラスの取得を行う。
    private val adapter: CategoryListAdapter by lazy {
        CategoryListAdapter { entity, view ->
            val controller = Navigation.findNavController(view)
            var rId = R.id.action_categoryListFragment_to_workBookListFragment
            val bundle     = Bundle().apply {
                    putParcelable(ARGS_KEY,entity)
                    putInt(ARGS_SIDE_MENU_FLAG,type)}

            if(type == MainActivity.actionStatisticsValue)
                rId = R.id.action_categoryFragment_to_statisticsFragment

            controller.navigate(rId,bundle)
        }
    }

    //分類一覧のビューモデルを作成
    private val viewModel:CategoryViewModel by lazy {
        CategoryViewModelFactory(app = activity?.application!!)
                .create(CategoryViewModel::class.java)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { a->
            resources.getStringArray(R.array.side_menu_keys).let{ f->
                f.forEach {
                    if(a.get(it) != null)type = a.getInt(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list_category,container,false)

        activity?.let {
            view.showSnackBar(it.getMag(type))
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycleInit()
        viewModel.let { vm ->
            vm.data.observe(viewLifecycleOwner){ data ->
                adapter.submitList(data)
            }
        }
        fab_category_add.also {
            it.isVisible = type.isHolder()
            it.setOnClickListener { executeDialog() }
        }

    }

    /**
     * ■リサイクラービューの初期化を行う。
     */
    private fun recycleInit(){
        category_recycle_view.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(activity)
        }
    }

    /**
     * ダイヤログを表示させ、表示させたテキストフィールド内の値を基に、
     * カテゴリーエンティティへデータの登録を行う。
     */
    private fun executeDialog(){

        val dialogCategory = activity?.let {
            CategoryDialogFactory(it,R.layout.dialog_category_layout)
                    .create(CategoryDialog::class.java)
        }

        dialogCategory?.let{ dg->
            val alertDialog = dg.create().also { it.show() }
            val (title,btn) = dg.getView().findViewById<TextInputEditText>(R.id.dialog_category_title_edit) to
                            dg.getView().findViewById<Button>(R.id.dialog_category_insert_btn)
            btn.setOnClickListener {
                viewModel.toInsert(title.text.toString())
                alertDialog.cancel()
            }
        }
    }


    /**
     * ■カテゴリーの項目を増やします。初期フラグは 0
     * @param title ダイヤログでの入力された値を引き数として渡してください
     */
    private fun CategoryViewModel.toInsert(title:String){
            insert(
                    QuestionCategoryEntity(
                        categoryNo = 0,
                        categoryFlag = 0,
                        categoryTitle = title,
                        timeStamp = LocalDateTime.now()))
        }



    companion object{
        const val ARGS_KEY  = "navigate_args_category_to_workBook"
        const val ARGS_SIDE_MENU_FLAG = "navigate_args_side_menu"
    }
}