package com.code_chabok.coinranking.feature.home

import android.content.res.Configuration
import android.graphics.Typeface
import android.icu.text.Transliterator
import android.os.Build
import com.code_chabok.coinranking.common.CoinActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.enableFullScreenMode
import com.code_chabok.coinranking.common.hide
import com.code_chabok.coinranking.common.show
import com.code_chabok.coinranking.databinding.ActivityMainBinding
import com.code_chabok.coinranking.feature.search.SearchFragment
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder


import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.switchmaterial.SwitchMaterial
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import android.view.animation.Animation
import android.view.animation.AnimationSet

import android.view.animation.RotateAnimation




@AndroidEntryPoint
class MainActivity : CoinActivity(), OnChangingFragmentListener {

    private lateinit var btnSwitchTheme: SwitchMaterial

    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    private lateinit var drawerToggle: ActionBarDrawerToggle

    private lateinit var configuration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        showingSplash()
        initUiComponents()
        enableFullScreenMode()
        initNavController()
        setUpToolBar()
        setupNavigationUiState()
        setupDrawerLayout()

        BubbleShowCaseBuilder(this)
            .title("You can Switch Between Tabs for more Detail")
            .targetView(binding.bottomNav)
            .showOnce("BUBBLE_SHOW_CASE_ID_3")
            .show()

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun showingSplash() {
       val alphaAnimation = AlphaAnimation(0F,1F)
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(alphaAnimation)

        animationSet.duration = 4000
        binding.tvLogo.animation = animationSet
        binding.ivLogo.animation = animationSet
        animationSet.fillAfter = true
        animationSet.start()
        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.splash.visibility = View.GONE
                binding.drawerLayout.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })

    }

    private fun initUiComponents() {
        btnSwitchTheme =
            binding.navigationView.menu.findItem(R.id.mi_night_mode).actionView as SwitchMaterial

        btnSwitchTheme.setOnCheckedChangeListener { _, isChecked ->
            val mode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }

            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(configuration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_search, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_search -> {
                navController.navigate(R.id.action_homeFragment_to_searchFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupNavigationUiState() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {

                R.id.homeFragment -> {
                    binding.bottomNav.show()
                    supportActionBar?.show()
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

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //todo we should setUpNavigationUiState because of refreshing navController
        // for understanding witch fragment is loading and behave approach(home->drawerLayout start else-> navigationUp)
        setupNavigationUiState()
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
        binding.toolbar.setNavigationOnClickListener {
            if (!isHome) {
                navController.navigateUp()
            } else {
                setupDrawerLayout()
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }


}

interface OnChangingFragmentListener {
    fun onChanged(isHome: Boolean)
}