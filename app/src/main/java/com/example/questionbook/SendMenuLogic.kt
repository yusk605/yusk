package com.example.questionbook

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation

/**
 * ■サイドメニューから渡される値を基に遷移を行う。
 * @param view      必要となるビュー
 * @param bundle    必要な値を格納するためのオブジェクト
 */
fun Int.actionWorkBook(view: View, bundle: Bundle){
        when(this){
            0 -> Navigation.findNavController(view)
                    .navigate(R.id.action_workBookFragment_to_problemListFragment,bundle)
            1 -> Navigation.findNavController(view)
                    .navigate(R.id.action_workBookFragment_to_gameStartFragment,bundle)
            2 -> Navigation.findNavController(view)
                    .navigate(R.id.action_workBookFragment_to_accuracyFragment,bundle)
        }
    }

