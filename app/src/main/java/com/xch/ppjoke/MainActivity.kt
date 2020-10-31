package com.xch.ppjoke

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xch.ppjoke.util.NavGraphBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val fragmentContainer = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!
        val navController = NavHostFragment.findNavController(fragmentContainer)
//        navView.setupWithNavController(navController)

        NavGraphBuilder.build(this, fragmentContainer.childFragmentManager, navController, fragmentContainer.id)

        navView.setOnNavigationItemSelectedListener {
            navController.navigate(it.itemId)
            it.title.isNotEmpty()
        }
    }
}