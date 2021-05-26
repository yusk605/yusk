package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.navigation.NavController
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


class CategoryListFragment : Fragment(){

    private var type = 0

    //アダプタークラスの取得を行う。
    private val adapter: CategoryListAdapter by lazy {
        CategoryListAdapter { entity, view ->

            val controller = Navigation.findNavController(view)
            var rId = R.id.action_categoryListFragment_to_workBookListFragment

            val bundle = Bundle().apply {
                    putParcelable(ARGS_KEY, entity)
                    putInt(ARGS_SIDE_MENU_FLAG, type)
                }

            when (type) {
                //統計画面の場合
                MainActivity.actionStatisticsValue -> {
                    rId = R.id.action_categoryFragment_to_statisticsFragment
                    controller.navigate(rId, bundle)
                }
                //タイプがホルダー以外の場合はポップアップメニューを表示する。
                MainActivity.actionHolderValue -> {
                    val popupMenu = PopupMenu(requireActivity(), view)
                    showPopup(popupMenu,entity,controller,rId,bundle)
                }
                else -> {
                    controller.navigate(rId,bundle)
                }
            }
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
        return inflater.inflate(R.layout.fragment_list_category,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycleInit()
        viewModel.let { vm ->
            vm.data.observe(viewLifecycleOwner){ data ->
                adapter.submitList(
                        data.filter { it.questionCategoryEntity.categoryFlag != 2 }
                )
            }
        }

        category_list_add_fab.also {
            it.isVisible = type.isHolder()
            it.setOnClickListener {
                insertDialog()
            }
        }

        activity?.let {
            var boolean = category_list_add_fab.isVisible
            var v:View = if(boolean) category_list_add_fab else category_list_recycle_view
            v.showSnackBar(it.getMag(type),boolean)
        }
    }

    /**
     * ■リサイクラービューの初期化を行う。
     */
    private fun recycleInit(){
        category_list_recycle_view.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(activity)
        }
    }

    /**
     * ▪カテゴリーの登録を行う。
     * ダイヤログを表示させ、表示させたテキストフィールド内の値を基に、
     * カテゴリーエンティティへデータの登録を行う。
     */
    private fun insertDialog(){

        val dialogCategory = activity?.let {
            CategoryDialogFactory(it,R.layout.dialog_category_layout)
                    .create(CategoryDialog::class.java)
        }

        dialogCategory?.let{ dg->
            val alertDialog = dg.create().also { it.show() }
            val (title,btn) = dg.getView().findViewById<TextInputEditText>(R.id.dialog_category_title_edit) to
                            dg.getView().findViewById<Button>(R.id.dialog_category_insert_btn)
            btn.setOnClickListener {
                val text = title.text.toString()
                var isDuplicate = false
                var isDuplicateSecond = false
                viewModel.data.observe(viewLifecycleOwner){
                    data->
                    val list = data.filter { it.questionCategoryEntity.categoryTitle == text }

                    //ダイヤログに入力したデータ（タイトル）の重複の確認
                    isDuplicate = false == list.any{it.questionCategoryEntity.categoryTitle==text}

                    //ゴミ箱に重複したタイトルの存在確認
                    isDuplicateSecond = list.any{it.questionCategoryEntity.categoryFlag==2}
                }

                //重複していないならインサートを行う。
                if (isDuplicate) {
                    viewModel.toInsert(title.text.toString())
                    alertDialog.cancel()
                }else {
                    var msg = ""
                    activity?.let {
                        msg = if(isDuplicateSecond)it.getString(R.string.error_dialog_category_0002)
                        else it.getString(R.string.error_dialog_category_0001)
                    }
                    title.enterAgain(msg)
                }
            }
        }
    }



    private fun updateDialog(entity: QuestionCategoryEntity){
        val dialogCategory = activity?.let {
            CategoryDialogFactory(it,R.layout.dialog_category_layout)
                    .create(CategoryDialog::class.java)
        }

        dialogCategory?.let{ dg->
            val alertDialog = dg.create().also {
                it.setTitle(getString(R.string.dialog_category_title))
                it.show()
            }
            val (title,btn) = dg.getView().findViewById<TextInputEditText>(R.id.dialog_category_title_edit) to
                    dg.getView().findViewById<Button>(R.id.dialog_category_insert_btn)

            btn.text = getString(R.string.dialog_update_button)
            title.setText(entity.categoryTitle)

            btn.setOnClickListener {
                viewModel.update(entity.apply {
                    entity.categoryTitle = title.text.toString()
                    timeStamp = LocalDateTime.now()
                })
                alertDialog.cancel()
            }
        }
    }


    private fun showPopup(
            popupMenu:PopupMenu,
            entity:QuestionCategoryEntity,
            controller: NavController,
            rId:Int,
            bundle: Bundle
    ){
        createPopup(popupMenu,R.menu.menu_popup).setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when(it.itemId){
                R.id.popup_menu_select_first ->{
                    controller.navigate(rId,bundle)
                    true
                }
                R.id.popup_menu_select_second  -> {
                    updateDialog(entity)
                    true
                }
                R.id.popup_menu_select_third -> {
                    viewModel.update(entity.also { e->e.categoryFlag = 2 })
                    true
                }
                else -> false
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