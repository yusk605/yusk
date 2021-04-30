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
                        R.id.categoryListFragment,
                        newBundleToPutInt(resources.getStringArray(R.array.side_menu_keys)[0], actionHolderValue)
                    )
                }
            R.id.game_list_fragment -> {
                controller.navigate(
                        R.id.categoryListFragment,
                        newBundleToPutInt(resources.getStringArray(R.array.side_menu_keys)[1], actionGameValue)
                    )
                }
            R.id.statistics_fragment ->{
                controller.navigate(
                        R.id.categoryListFragment,
                        newBundleToPutInt(resources.getStringArray(R.array.side_menu_keys)[2], actionStatisticsValue)
                    )
                }
            R.id.history_list_fragment -> {
                controller.navigate(
                        R.id.categoryListFragment,
                        newBundleToPutInt(resources.getStringArray(R.array.side_menu_keys)[3], actionHistory)
                    )
                }
            }
        drawerLayout.close()
        return true
    }

    /**
     * ■バンドルでの保存領域に格納する値として。
     * putInt(key,value)
     */
    companion object{
         const val actionHolderValue        = 0
         const val actionGameValue          = 1
         const val actionStatisticsValue    = 2
         const val actionHistory            = 3
         const val actionGarbageCan         = 4
    }
}