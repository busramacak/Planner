package com.bmprj.planner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bmprj.planner.R
import com.bmprj.planner.databinding.ActivityMainBinding
import com.bmprj.planner.utils.setDrawable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding:ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navHostFragment.navController
        )

        binding.fab.setOnClickListener {
            viewModel.onFabClicked()
        }

        navHostFragment.navController.addOnDestinationChangedListener{_, d:NavDestination,_->
            if(d.id== R.id.addNoteFragment || d.id==R.id.addTaskFragment || d.id == R.id.showNoteFragment){
                hideBottomNav()
            }
            else{
                showBottomNav()
            }

            if(d.id == R.id.notesFragment){
            binding.fab.setDrawable("note")
            }
            if( d.id == R.id.tasksFragment){
            binding.fab.setDrawable("task")
        }
        }


    }

    private fun showBottomNav() {
        binding.bottomNavigationView.visibility=View.VISIBLE
        binding.fab.visibility=View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility= View.GONE
        binding.fab.visibility=View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}