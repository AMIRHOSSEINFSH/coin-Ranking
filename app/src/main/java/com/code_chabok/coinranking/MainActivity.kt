package com.code_chabok.coinranking

import android.content.res.Configuration
import com.code_chabok.coinranking.common.CoinActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.code_chabok.coinranking.common.hide
import com.code_chabok.coinranking.common.show
import com.code_chabok.coinranking.databinding.ActivityMainBinding

import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : CoinActivity() {

    private lateinit var navController: NavController

    private lateinit var binding :ActivityMainBinding

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        initNavController()
        setUpToolBar()
        setupDrawerLayout()
        setupNavigationUiState()


        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_search, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun setupNavigationUiState() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.homeFragment->{
                    binding.toolbar.show()
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
                R.id.exchangesFragment -> {
                    binding.toolbar.hide()
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

                R.id.bookMarksFragment -> {
                    binding.toolbar.hide()
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        drawerToggle.syncState()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    private fun setUpToolBar() {
        setSupportActionBar(binding.toolbar)

        val configuration = AppBarConfiguration.Builder(setOf(R.id.homeFragment,R.id.bookMarksFragment,R.id.exchangesFragment))
            .setOpenableLayout(binding.drawerLayout)
            .build()
        setupActionBarWithNavController(navController, configuration)
    }

    private fun setupDrawerLayout() {
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.action_open_drawer,
            R.string.action_close_drawer
        )

        binding.drawerLayout.addDrawerListener(drawerToggle)
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        navController = navHostFragment.navController
    }




}