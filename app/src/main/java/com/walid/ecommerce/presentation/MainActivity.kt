package com.walid.ecommerce.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.walid.ecommerce.R
import com.walid.ecommerce.common.delegate.viewBinding
import com.walid.ecommerce.common.gone
import com.walid.ecommerce.common.visible
import com.walid.ecommerce.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.splashFragment,
                R.id.signInFragment,
                R.id.signUpFragment,
                R.id.forgotPasswordFragment,
                R.id.paymentSuccessFragment -> {
                    binding.bottomNav.gone()
                    binding.floatingActionButton.gone()
                }

                else -> {
                    binding.bottomNav.visible()
                    binding.floatingActionButton.visible()
                }
            }
        }

        binding.floatingActionButton.setOnClickListener {
            navController.navigate(R.id.searchFragment)
        }
    }
}