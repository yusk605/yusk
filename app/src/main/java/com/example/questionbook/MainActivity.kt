package com.example.questionbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.questionbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        latainit()
        binding = setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        NavigationUI.setupActionBarWithNavController(this,controller,drawerLayout)
        NavigationUI.setupWithNavController(binding.navigationView,controller)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(controller,drawerLayout)
    }

    /**
     * バッキングフィールドへ初期化を行うメソッド。
     */
    private fun latainit(){
        drawerLayout = binding.drawerLayout
        controller = this.findNavController(R.id.nav_controller_view_tag)
    }

}