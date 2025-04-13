package com.example.finbot
import android.view.MenuItem
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.finbot.databinding.ActivityMainBinding
import com.example.finbot.fragment.earningFragment
import com.example.finbot.fragment.profileFragment
import com.example.finbot.fragment.statFragment
import com.example.finbot.fragments.homeFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the default fragment (HomeFragment)
        loadFragment(homeFragment())

        // Set up bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(homeFragment())
                    true
                }
                R.id.nav_stat -> {
                    loadFragment(statFragment())
                    true
                }
                R.id.nav_earning -> {
                    loadFragment(earningFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(profileFragment())
                    true
                }
                else -> false
            }
        }
    }

    // Helper function to load fragments
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}