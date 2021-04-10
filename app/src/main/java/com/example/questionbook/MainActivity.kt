package com.example.questionbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil.setContentView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.questionbook.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private val controller: NavController by lazy{
        this.findNavController(R.id.fragment_host)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        NavigationUI.setupActionBarWithNavController(this,controller,drawerLayout)
        navigation_view.setNavigationItemSelectedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(controller,drawerLayout)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.holder_list_fragment -> {
                controller.navigate(
                        R.id.categoryFragment,
                        newBundleToPutInt(getString(R.string.side_menu_key_holder,), actionHolderValue)
                    )
                }
            R.id.game_list_fragment -> {
                controller.navigate(
                        R.id.laboratoryFragment,
                        newBundleToPutInt(getString(R.string.side_menu_key_game), actionGameValue)
                    )
                }
            R.id.accuracy_list_fragment ->{
                controller.navigate(
                        R.id.accuracyFragment,
                        newBundleToPutInt(getString(R.string.side_menu_key_accuracy), actionAccuracyValue)
                    )
                }
            R.id.garbage_can_fragment -> {
                controller.navigate(
                        R.id.garbageCanFragment,
                        newBundleToPutInt(getString(R.string.side_menu_key_garbagecan), actionGarbageCan)
                    )
                }
        }
        drawerLayout.close()
        return true
    }

    /**
     * ■バンドルを新規で生成したのち、値を格納する。
     * @param key   値を保存領域に格納しその格納した値を取り出すためのキーを指定
     * @param value 保存領域に格納する値
     */
    private fun newBundleToPutInt(key:String, value:Int)=
        Bundle().apply {
            putInt(key,value)
    }

    /**
     * ■バンドルでの保存領域に格納する値として。
     * putInt(key,value)
     */
    companion object{
        private const val actionHolderValue     = 0
        private const val actionGameValue       = 1
        private const val actionAccuracyValue   = 2
        private const val actionGarbageCan      = 3
    }
}