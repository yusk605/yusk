package com.example.questionbook.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ConcreteResultList:ListAdapter<>() {

    companion object Diff:DiffUtil.ItemCallback<>

    inner  class ConcreteResultListHolder(view:View):RecyclerView.ViewHolder(view){

    }
}