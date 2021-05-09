package com.example.questionbook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.questionbook.GarbageCanActionButton
import com.example.questionbook.MainActivity
import com.example.questionbook.R
import com.example.questionbook.newBundleToPutInt

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

        //TODO ボタンをクリックした時の処理を記述すること。　イベントハンドラーの場合はエラーが出るため。

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
            R.id.garbage_can_category_button -> bundle.putInt(
                GarbageCanActionButton.CATEGORY.name.hashCode().toString(),
                GarbageCanActionButton.CATEGORY.get
            )
            R.id.garbage_can_workbook_button -> bundle.putInt(
                GarbageCanActionButton.WORKBOOK.name.hashCode().toString(),
                GarbageCanActionButton.WORKBOOK.get
            )
            R.id.garbage_can_leaf_button    -> bundle.putInt(
                GarbageCanActionButton.LEAF.name.hashCode().toString(),
                GarbageCanActionButton.LEAF.get
            )
        }
        Navigation.findNavController(view)
            .navigate(
                R.id.action_garbageCanFragment_to_garbageCanListFragment,
                bundle
            )
        }

    companion object { }
}