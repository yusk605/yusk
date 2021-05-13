package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.questionbook.GarbageCanActionButton
import com.example.questionbook.MainActivity
import com.example.questionbook.R
import com.example.questionbook.newBundleToPutInt
import kotlinx.android.synthetic.main.fragment_garbage_can.*

class GarbageCanFragment : Fragment() {

    private var actionGarbageCan = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            actionGarbageCan = it.getInt(
                resources.getStringArray(R.array.side_menu_keys)[4]
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_garbage_can, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.safety()
        val bundle  =   Bundle()

        val controller:NavController = Navigation.findNavController(view)

        //分類一覧の項目を表示させる。
        garbage_can_category_button.setOnClickListener {
            bundle.putInt(
                    ARGS_KEY,
                    GarbageCanActionButton.CATEGORY.get
            )
            controller.navigate(R.id.action_garbageCanFragment_to_garbageCanListFragment,bundle)
        }

        //問題集一覧の項目を表示させる。
        garbage_can_workbook_button.setOnClickListener {
            bundle.putInt(
                    ARGS_KEY,
                    GarbageCanActionButton.WORKBOOK.get
            )
            controller.navigate(R.id.action_garbageCanFragment_to_garbageCanListFragment,bundle)
        }

        //問題文一覧の項目を表示させる。
        garbage_can_leaf_button.setOnClickListener {
            bundle.putInt(
                    ARGS_KEY,
                    GarbageCanActionButton.LEAF.get
            )
            controller.navigate(R.id.action_garbageCanFragment_to_garbageCanListFragment,bundle)
        }
    }

    /**
     * ■渡される値が異なる場合カテゴリー一覧へ遷移する。
     * サイドメニューから任意の項目をタップされた場合は何もしない
     */
    private fun View.safety(){
        if (actionGarbageCan == 0)
            Navigation.findNavController(this).navigate(
                R.id.categoryListFragment,
                newBundleToPutInt(
                    resources.getStringArray(R.array.side_menu_keys)[0],
                    MainActivity.actionHolderValue
                ))
    }

    /**
     * ■フラグメントのonClickメソッドに紐づいたハンドラーから呼ばれるメソッド
     * @param view fragment_garbage_can_list の　onClick属性に使用されています。
     */
    fun actionButton(view: View){
        val bundle  =   Bundle()
        when(view.id){
            R.id.garbage_can_category_button ->
                bundle.putInt(
                    ARGS_KEY, GarbageCanActionButton.CATEGORY.get
                )
            R.id.garbage_can_workbook_button ->
                bundle.putInt(
                    ARGS_KEY, GarbageCanActionButton.WORKBOOK.get
                )
            R.id.garbage_can_leaf_button ->
                bundle.putInt(
                    ARGS_KEY, GarbageCanActionButton.LEAF.get
                )
        }
        Navigation.findNavController(view)
            .navigate(R.id.action_garbageCanFragment_to_garbageCanListFragment, bundle)
        }

    companion object {
        const val ARGS_KEY = "garbageCanFragment_to_garbageCanListFragment"
    }
}