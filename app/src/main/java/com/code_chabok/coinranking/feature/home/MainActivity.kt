package com.code_chabok.coinranking.feature.home

import android.content.res.Configuration
import com.code_chabok.coinranking.common.CoinActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.databinding.ActivityMainBinding

import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : CoinActivity(), OnChangingFragmentListener {


    private lateinit var navController: NavController

    private lateinit var binding :ActivityMainBinding

    private lateinit var drawerToggle: ActionBarDrawerToggle

    private lateinit var configuration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initNavController()
        setUpToolBar()
        setupNavigationUiState()
        setupDrawerLayout()

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(configuration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_search, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mi_search -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupNavigationUiState() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.homeFragment ->{
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    onChanged(true)
                }
                R.id.exchangesFragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    onChanged(false)
                }
                R.id.bookMarksFragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    onChanged(false)
                }
                R.id.searchFragment -> {
                    onChanged(false)

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

         configuration = AppBarConfiguration.Builder(setOf(R.id.homeFragment))
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
        //drawerToggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.action_open_drawer, R.string.action_open_drawer)
        //drawerToggle = ActionBarDrawerToggle(this,null,R.string.action_open_drawer,R.string.action_open_drawer)

        binding.drawerLayout.addDrawerListener(drawerToggle)
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onChanged(isHome: Boolean) {
        binding.toolbar.setNavigationOnClickListener{
            if(!isHome){
            navController.navigateUp()
            }else{
                setupDrawerLayout()
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }


}

interface OnChangingFragmentListener{
    fun onChanged(isHome: Boolean)
}